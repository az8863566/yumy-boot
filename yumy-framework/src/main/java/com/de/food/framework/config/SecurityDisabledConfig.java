package com.de.food.framework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security 关闭时的配置
 * <p>
 * 当 security.enabled=false 时生效，放行所有请求，无需认证。
 * <p>
 * 注意：此类不添加 @EnableMethodSecurity，因此 @PreAuthorize 注解不会生效，
 * 方法级权限校验自动关闭。
 */
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "security.enabled", havingValue = "false", matchIfMissing = false)
public class SecurityDisabledConfig {

    @Bean
    public SecurityFilterChain disabledSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/**")
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    /**
     * 密码编码器 Bean
     * <p>
     * 即使 Security 关闭，业务层仍需要此 Bean 进行密码加密/校验。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器 Bean
     * <p>
     * 即使 Security 关闭，AuthServiceImpl 仍需要此 Bean 进行用户认证。
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
