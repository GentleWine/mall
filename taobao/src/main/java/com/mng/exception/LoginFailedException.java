package com.mng.exception;

public class LoginFailedException extends AuthenticationException {
    public LoginFailedException(String message) {
        super(message);
    }
}
