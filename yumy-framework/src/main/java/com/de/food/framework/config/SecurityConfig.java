package com.de.food.framework.config;

import com.de.food.common.result.Result;
import com.de.food.framework.security.JwtAuthenticationFilter;
import com.de.food.framework.security.JsonAccessDeniedHandler;
import com.de.food.framework.security.JsonAuthenticationEntryPoint;
import com.de.food.framework.util.JwtUtil;
import tools.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Security 配置
 * <p>
 * 配置两套过滤器链，共用一套用户体系（UserDetailsService）：
 * <ul>
 *   <li>API 链（Order=1）：处理 /api/toc/** 路由，JWT 无状态认证</li>
 *   <li>Admin 链（Order=2）：处理 /admin/** 路由，Session 有状态认证</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Value("${cors.allowed-origins:*}")
    private String allowedOrigins;

    public SecurityConfig(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    /**
     * API 过滤器链 —— JWT 无状态认证，处理 /api/toc/** 路由
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/toc/**")
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(apiCorsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new JsonAuthenticationEntryPoint(objectMapper))
                .accessDeniedHandler(new JsonAccessDeniedHandler(objectMapper))
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/toc/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/toc/v1/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/toc/v1/recipes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/toc/v1/dict/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Admin 过滤器链 —— Session 有状态认证，处理后台管理路由
     */
    @Bean
    @Order(2)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/admin/**", "/swagger-ui/**", "/v3/api-docs/**")
            .csrf(csrf -> csrf.spa())
            .cors(cors -> cors.configurationSource(apiCorsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new JsonAuthenticationEntryPoint(objectMapper))
                .accessDeniedHandler(new JsonAccessDeniedHandler(objectMapper))
            )
            .authorizeHttpRequests(auth -> auth
                // SpringDoc 放行
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // 登录放行
                .requestMatchers("/admin/v1/auth/login").permitAll()
                // 上传文件静态资源放行
                .requestMatchers("/upload/**").permitAll()
                // 其他请求需认证
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutUrl("/admin/v1/auth/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(objectMapper.writeValueAsString(Result.ok()));
                })
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
            );

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * CORS 配置
     * <p>
     * 从配置文件读取允许的源，同时服务于 API 链和 Admin 链：
     * <ul>
     *   <li>dev/local 环境：允许所有源（*）</li>
     *   <li>prod 环境：单独配置允许的源列表</li>
     * </ul>
     */
    @Bean
    public CorsConfigurationSource apiCorsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 解析允许的源（支持逗号分隔的多个源）
        // 注意：allowCredentials=true 时不能使用 allowedOrigins("*")，必须用 allowedOriginPatterns
        if ("*".equals(allowedOrigins)) {
            config.setAllowedOriginPatterns(List.of("*"));
        } else {
            config.setAllowedOriginPatterns(Arrays.asList(allowedOrigins.split(",")));
        }
        
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
