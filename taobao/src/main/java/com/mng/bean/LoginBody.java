package com.mng.bean;

import lombok.Data;

@Data
public class LoginBody {
    private String phone;
    private String password;
    private String usertype;
}
