package com.mng.exception.authentication;

public class LoginFailedException extends AuthenticationException {

    private final Status status;

    public LoginFailedException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public LoginFailedException(Status status) {
        super(status.getDefaultMessage());
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String getStatusName() {
        return status.toString();
    }

    @Override
    public String getStatusDescription() {
        return status.defaultMessage;
    }

    public enum Status {
        SUCCESS("Success!"),
        ACCOUNT_NOT_FOUND("This account does not exist!"),
        PASSWORD_INCORRECT("Password Incorrect!"),
        FIELD_MISSING("You must fill in all of the required fields when logging in!"),
        UNKNOWN("Unknown Error!");

        private final String defaultMessage;

        Status(String msg) {
            this.defaultMessage = msg;
        }

        public String getDefaultMessage() {
            return defaultMessage;
        }
    }
}
