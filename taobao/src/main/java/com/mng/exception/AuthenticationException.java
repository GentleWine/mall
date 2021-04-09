package com.mng.exception;

/**
 * 登录与注册异常的基类，表明输入的账户信息或密码错误
 */
public class AuthenticationException extends IllegalArgumentException {
    public AuthenticationException(String message) {
        super(message);
    }
}
