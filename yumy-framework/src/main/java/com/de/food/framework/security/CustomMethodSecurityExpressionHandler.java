package com.de.food.framework.security;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * 自定义方法级安全表达式处理器
 * <p>
 * 基于 Spring Security 7 {@code AuthorizationManagerFactory} 架构：
 * 使用 {@link CustomDefaultAuthorizationManagerFactory} 为超管提供旁路授权，
 * 使用 {@link CustomMethodSecurityExpressionRoot} 作为表达式根对象。
 */
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomMethodSecurityExpressionHandler.class);

    public CustomMethodSecurityExpressionHandler() {
        setAuthorizationManagerFactory(new CustomDefaultAuthorizationManagerFactory());
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, MethodInvocation invocation) {
        log.debug("使用自定义表达式处理器, user={}, authorities={}",
                authentication.getName(), authentication.getAuthorities());
        CustomMethodSecurityExpressionRoot root = new CustomMethodSecurityExpressionRoot(
                () -> authentication, invocation);
        root.setPermissionEvaluator(getPermissionEvaluator());
        return root;
    }
}
