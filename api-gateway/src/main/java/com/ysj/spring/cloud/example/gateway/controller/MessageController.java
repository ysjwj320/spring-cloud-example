package com.ysj.spring.cloud.example.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysj.spring.cloud.example.gateway.service.HelloWorldMessageService;

import reactor.core.publisher.Mono;

@RestController
public class MessageController {
    private final HelloWorldMessageService messages;

    public MessageController(HelloWorldMessageService messages) {
        this.messages = messages;
    }

    @GetMapping("/message")
    public Mono<String> message() {
        return this.messages.findMessage();
    }

}