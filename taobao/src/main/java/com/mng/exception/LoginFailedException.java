package com.mng.exception;

public class LoginFailedException extends AuthenticationException {

    private final ErrorType errorType;

    public LoginFailedException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public enum ErrorType {
        SUCCESS,
        ACCOUNT_NOT_FOUND,
        PASSWORD_INCORRECT;
    }
}
