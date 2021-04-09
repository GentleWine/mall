package com.mng.exception;

public class LoginFailedException extends AuthenticationException {

    private final ErrorType errorType;

    public LoginFailedException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public LoginFailedException(ErrorType errorType) {
        super(errorType.getDefaultMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public enum ErrorType {
        SUCCESS("Success!"),
        ACCOUNT_NOT_FOUND("This account does not exist!"),
        PASSWORD_INCORRECT("Password Incorrect!"),
        FIELD_MISSING("You must fill in all the fields when logging in!"),
        UNKNOWN("Unknown Error!");

        private final String defaultMessage;

        ErrorType(String msg) {
            this.defaultMessage = msg;
        }

        public String getDefaultMessage() {
            return defaultMessage;
        }
    }
}
