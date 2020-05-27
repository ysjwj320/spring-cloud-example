package com.ysj.spring.cloud.example.gateway.security.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class CustomAuthenticatonToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 2897127563197610682L;

    private final String accessToken;

    public CustomAuthenticatonToken(String accessToken) {
        super(null);
        this.accessToken = accessToken;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public String getCredentials() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getPrincipal() {
        return accessToken;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(accessToken).build();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomAuthenticatonToken) {
            return StringUtils.equals(((CustomAuthenticatonToken) obj).getPrincipal(), this.getPrincipal());
        }
        return false;
    }

}
