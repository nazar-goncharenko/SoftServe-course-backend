package com.softserve.app.constant;

public enum SportHubConstant {

    /*
    If necessary, enter more constants
     */

    // negative
    AUTHORIZE_EXCEPTION("Sorry, you're not authorized to access this resource"),
    USER_NOT_FOUND("User not found"),
    AUTHENTICATION_EXCEPTION("User authentication was failed"),
    JWT_EXCEPTION("Unable to get JWT Token or JWT Token has expired"),
    EMAIL_SENDING_EXCEPTION("Exception while performing email sending"),

    // positive
    REGISTERED_SUCCESSFULLY("Registered Successfully %s"),
    PASSWORD_RESTORE_MESSAGE("Hello %s, here your new password : %s"),
    PASSWORD_RESTORE_SUBJECT("Password restoration for User")
    ;

    private String message;
    SportHubConstant(String message){this.message = message;}
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "SportHubConstant{" +
                "message='" + message + '\'' +
                '}';
    }
}
