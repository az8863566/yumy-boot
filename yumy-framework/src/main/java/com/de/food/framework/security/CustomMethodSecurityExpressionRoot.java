package com.de.food.framework.security;

import java.util.function.Supplier;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import static com.de.food.framework.security.SecurityConstants.SUPER_ADMIN_ROLE;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot<MethodInvocation>
        implements MethodSecurityExpressionOperations {

    private final boolean superAdmin;
    private Object filterObject;
    private Object returnObject;
    private final Object target;

    public CustomMethodSecurityExpressionRoot(Supplier<Authentication> authentication, MethodInvocation invocation) {
        super(authentication, invocation);
        Authentication auth = authentication.get();
        this.superAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(g -> SUPER_ADMIN_ROLE.equals(g.getAuthority()));
        this.target = invocation != null ? invocation.getThis() : null;
    }

    @Override
    public boolean hasPermission(Object target, Object permission) {
        return superAdmin || super.hasPermission(target, permission);
    }

    @Override
    public boolean hasPermission(Object targetId, String targetType, Object permission) {
        return superAdmin || super.hasPermission(targetId, targetType, permission);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }
}
