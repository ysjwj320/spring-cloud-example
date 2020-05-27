package com.ysj.spring.cloud.example.gateway.security.converter;

import com.ysj.spring.cloud.example.gateway.security.dto.CustomAuthenticatonToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@Component
public class BearerTokenAuthenticationConverter implements ServerAuthenticationConverter {
    private static final String AUTHORIZATION_PREFIX = "Bearer ";
    private static final Predicate<String> BEARER_TOKEN_PREDICATE = s -> StringUtils.isNotBlank(s)
            && StringUtils.startsWithIgnoreCase(s, AUTHORIZATION_PREFIX);
    private static final UnaryOperator<String> SPLIT_TOKEN = s -> StringUtils.substring(s, AUTHORIZATION_PREFIX.length());

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> values = headers.get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(values)) {
            return Mono.empty();
        }
        Optional<CustomAuthenticatonToken> authenticationOp = values.parallelStream()
                .filter(BEARER_TOKEN_PREDICATE).map(SPLIT_TOKEN)
                .map(CustomAuthenticatonToken::new).findFirst();
        return Mono.justOrEmpty(authenticationOp);
    }

}
