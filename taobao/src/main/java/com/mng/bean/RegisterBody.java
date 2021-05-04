package com.mng.bean;

import lombok.Data;

@Data
public class RegisterBody {
    private String username;
    private String password;
    private String confirm;
    private String phone;
    private String mail;
    private String usertype;
    private boolean agreementAgreed;
}
