package com.mng.bean;

import lombok.Data;

@Data
public class UserAddBody {
    private String username;
    private String password;
    private String usertype;
    private String phone;
    private String mail;
}
