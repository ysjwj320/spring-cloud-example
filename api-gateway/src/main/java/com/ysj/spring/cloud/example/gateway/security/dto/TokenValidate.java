package com.ysj.spring.cloud.example.gateway.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenValidate {

    private String accessToken;

}
