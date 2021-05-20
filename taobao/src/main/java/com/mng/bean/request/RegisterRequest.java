package com.mng.bean.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String confirm;
    private String phone;
    private String mail;
    private String usertype;
    private Boolean agreementAgreed;
}
