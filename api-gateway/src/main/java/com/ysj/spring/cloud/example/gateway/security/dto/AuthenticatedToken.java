package com.ysj.spring.cloud.example.gateway.security.dto;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class AuthenticatedToken extends AbstractAuthenticationToken {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -3686731722345703833L;

    private final String userPrincipal;

    public AuthenticatedToken() {
        this(null);
    }

    public AuthenticatedToken(String userPrincipal) {
        super(Collections.emptyList());
        this.userPrincipal = userPrincipal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getPrincipal() {
        return StringUtils.EMPTY;
    }

    public String getUserPrincipal() {
        return userPrincipal;
    }

}
