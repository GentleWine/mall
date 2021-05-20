package com.mng.bean.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    private String phone;
    private String password;
    private Boolean rememberMe;
}
