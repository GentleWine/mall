package com.mng.exception.authentication;

/**
 * 登录与注册异常的基类，表明输入的账户信息或密码错误
 */
public abstract class AuthenticationException extends IllegalArgumentException {
    public AuthenticationException(String message) {
        super(message);
    }

    public abstract String getStatusName();

    public abstract String getStatusDescription();
}
