package com.mng.exception;

public class RegisterFailedException extends AuthenticationException {
    public RegisterFailedException(String message) {
        super(message);
    }
}
