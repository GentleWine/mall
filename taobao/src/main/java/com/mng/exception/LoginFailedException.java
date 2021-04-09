package com.mng.exception;

public class LoginFailedException extends AuthenticationException {
    // TODO: 处理异常类型，如密码错误，缺少参数等
    public LoginFailedException(String message) {
        super(message);
    }
}
