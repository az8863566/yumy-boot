package com.de.food.framework.security;

import java.util.function.Supplier;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagerFactory;
import org.springframework.security.authorization.DefaultAuthorizationManagerFactory;
import org.springframework.security.core.Authentication;

import static com.de.food.framework.security.SecurityConstants.SUPER_ADMIN_ROLE;

public class CustomDefaultAuthorizationManagerFactory implements AuthorizationManagerFactory<MethodInvocation> {

    private final DefaultAuthorizationManagerFactory<MethodInvocation> delegate = new DefaultAuthorizationManagerFactory<>();

    private boolean isSuperAdmin(Supplier<? extends Authentication> authentication) {
        Authentication auth = authentication.get();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(g -> SUPER_ADMIN_ROLE.equals(g.getAuthority()));
    }

    public void setTrustResolver(org.springframework.security.authentication.AuthenticationTrustResolver trustResolver) {
        delegate.setTrustResolver(trustResolver);
    }

    public void setRoleHierarchy(org.springframework.security.access.hierarchicalroles.RoleHierarchy roleHierarchy) {
        delegate.setRoleHierarchy(roleHierarchy);
    }

    public void setRolePrefix(String rolePrefix) {
        delegate.setRolePrefix(rolePrefix);
    }

    @Override
    public AuthorizationManager<MethodInvocation> hasRole(String role) {
        return wrap(delegate.hasRole(role));
    }

    @Override
    public AuthorizationManager<MethodInvocation> hasAnyRole(String... roles) {
        return wrap(delegate.hasAnyRole(roles));
    }

    @Override
    public AuthorizationManager<MethodInvocation> hasAllRoles(String... roles) {
        return wrap(delegate.hasAllRoles(roles));
    }

    @Override
    public AuthorizationManager<MethodInvocation> hasAuthority(String authority) {
        return wrap(delegate.hasAuthority(authority));
    }

    @Override
    public AuthorizationManager<MethodInvocation> hasAnyAuthority(String... authorities) {
        return wrap(delegate.hasAnyAuthority(authorities));
    }

    @Override
    public AuthorizationManager<MethodInvocation> hasAllAuthorities(String... authorities) {
        return wrap(delegate.hasAllAuthorities(authorities));
    }

    @Override
    public AuthorizationManager<MethodInvocation> authenticated() {
        return wrap(delegate.authenticated());
    }

    @Override
    public AuthorizationManager<MethodInvocation> fullyAuthenticated() {
        return wrap(delegate.fullyAuthenticated());
    }

    @Override
    public AuthorizationManager<MethodInvocation> rememberMe() {
        return wrap(delegate.rememberMe());
    }

    @Override
    public AuthorizationManager<MethodInvocation> anonymous() {
        return delegate.anonymous();
    }

    private AuthorizationManager<MethodInvocation> wrap(AuthorizationManager<MethodInvocation> delegate) {
        return (authentication, invocation) -> isSuperAdmin(authentication)
                ? new AuthorizationDecision(true)
                : delegate.authorize(authentication, invocation);
    }
}
