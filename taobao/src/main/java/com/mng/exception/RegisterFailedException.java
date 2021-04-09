package com.mng.exception;

public class RegisterFailedException extends AuthenticationException {
    // TODO: 处理异常类型，如用户重复，缺少参数等
    public RegisterFailedException(String message) {
        super(message);
    }
}
