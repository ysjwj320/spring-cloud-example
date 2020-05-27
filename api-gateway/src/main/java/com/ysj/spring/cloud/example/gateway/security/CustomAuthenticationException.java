package com.ysj.spring.cloud.example.gateway.security;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = -4483252340297208090L;

    private final String failedType;

    public CustomAuthenticationException(String failedType) {
        super("");
        this.failedType = failedType;
    }

    public CustomAuthenticationException(Throwable cause) {
        this("UNKNOW_ERROR", cause);
    }

    public CustomAuthenticationException(String failedType, String message) {
        this(failedType, null, message);
    }

    public CustomAuthenticationException(String failedType, Throwable cause) {
        this(failedType, cause, null);
    }

    public CustomAuthenticationException(String failedType, Throwable cause, String message) {
        super(message, cause);
        this.failedType = failedType;

    }

    public String getFailedType() {
        return failedType;
    }

}
