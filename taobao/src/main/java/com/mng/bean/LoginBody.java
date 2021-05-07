package com.mng.bean;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginBody {
    private String phone;
    private String password;
    private boolean rememberMe;
}
