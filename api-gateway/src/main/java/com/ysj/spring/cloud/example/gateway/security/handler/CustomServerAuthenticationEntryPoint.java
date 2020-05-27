package com.ysj.spring.cloud.example.gateway.security.handler;

import com.ysj.spring.cloud.example.gateway.security.CustomAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        log.info("request path: {}, authentication failed", exchange.getRequest().getPath());
        String responseString = "authentication failed";
        if (e instanceof CustomAuthenticationException) {
            CustomAuthenticationException ex = (CustomAuthenticationException) e;
            responseString += ", cause: " + ex.getFailedType();
        }
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory factory = response.bufferFactory();
        DataBuffer data = factory.wrap(responseString.getBytes(StandardCharsets.UTF_8));
        Mono.just(data).log().subscribe(d -> log.info("wirte response string: {}", d.toString(StandardCharsets.UTF_8)));
        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        return response.writeAndFlushWith(Mono.just(Mono.just(data).log()));
    }

}
