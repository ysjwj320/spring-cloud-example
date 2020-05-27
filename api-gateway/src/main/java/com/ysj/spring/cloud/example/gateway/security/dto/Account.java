package com.ysj.spring.cloud.example.gateway.security.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Account implements Serializable {
    private String id;
    private String username;
    private String password;
    private Date createTime;
    private Date lastLoginTime;
}
