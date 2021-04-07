package com.mng.bean;

import lombok.Data;

@Data
public class Registerbody {
    private String username;
    private String password;
    private String confirm;
    private String phone;
    private String mail;
    private String usertype;
}
