package com.mng.bean;

import lombok.Data;

@Data
public class UserEditBody {
    private int id;
    private String username;
    private String password;
    private String usertype;
    private String phone;
    private String mail;
}
