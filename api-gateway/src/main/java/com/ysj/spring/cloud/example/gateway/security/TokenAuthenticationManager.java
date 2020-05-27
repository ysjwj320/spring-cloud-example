package com.ysj.spring.cloud.example.gateway.security;

import com.ysj.spring.cloud.example.gateway.security.dto.CustomAuthenticatonToken;
import com.ysj.spring.cloud.example.gateway.security.dto.TokenValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TokenAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    @Qualifier("authentication-service")
    private WebClient webClient;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomAuthenticatonToken token = (CustomAuthenticatonToken) authentication;
        log.info("custom authentication: {}", AuthenticationException.class.isInstance(new CustomAuthenticationException("INVALID_USER_PASSWORD")));
        return webClient.post().uri("/api/token/check")
                .body(Mono.just(new TokenValidate(token.getPrincipal())), TokenValidate.class)
                .exchange()
                .flatMap(c -> Mono.error(new CustomAuthenticationException("test token check failed")));
    }

}
