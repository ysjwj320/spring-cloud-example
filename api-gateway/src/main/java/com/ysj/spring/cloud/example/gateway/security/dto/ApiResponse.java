package com.ysj.spring.cloud.example.gateway.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public  final class ApiResponse {
    private int code = 200;
    private String message;
    private String content;
}