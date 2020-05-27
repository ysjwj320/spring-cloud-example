package com.ysj.spring.cloud.example.gateway.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class HelloWorldMessageService {

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<String> findMessage() {
        return Mono.just("Hello World!");
    }

}
