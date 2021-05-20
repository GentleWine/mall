package com.mng.bean.request;

import lombok.Data;

@Data
public class UserAddRequest {
    private String username;
    private String password;
    private String usertype;
    private String phone;
    private String mail;
}
