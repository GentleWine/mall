package com.mng.exception.authentication;

public class RegisterFailedException extends AuthenticationException {

    private final Status status;

    public RegisterFailedException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public RegisterFailedException(Status status) {
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
        USER_ALREADY_EXISTS("A user with this phone number already exists!"),
        PASSWORD_CONFIRM_MISMATCH("Your confirmed password does not match with the original password!"),
        FIELD_MISSING("You must fill in all of the required fields when registering!"),
        INVALID_USER_TYPE("User type is invalid!"),
        INVALID_EMAIL("Invalid e-mail address!"),
        INVALID_PHONE("Invalid phone number!"),
        PASSWORD_TOO_SHORT("Your password must be not less than 6 characters!"),
        INVALID_USERNAME("Your username must be 3-18 characters containing only letters, numbers and underscores!"),
        AGREEMENT_NOT_AGREED("You must agree the user agreement to continue!"),
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
