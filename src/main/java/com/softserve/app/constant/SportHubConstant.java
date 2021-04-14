package com.softserve.app.constant;

public enum SportHubConstant {

    /// emailSender constants
    EMAIL_SENT("Email sent"),
    COMPANY_EMAIL("SportHub@gmail.com"),
    EMAIL_TITLE("Here's the link to reset your password"),
    COMPANY_NAME("SportHub support"),
    EMAIL_TEXT_P1("<p>Hello,</p>"
            + "<p>You have requested to reset your password.</p>"
            + "<p>Click the link below to change your password:</p>"
            + "<p><a href=\""),
    EMAIL_TEXT_P2("\">Change my password</a></p>"
            + "<br>"
            + "<p>Ignore this email if you do remember your password, "
            + "or you have not made the request.</p>"),


    AUTHORIZE_EXCEPTION("Sorry, you're not authorized to access this resource"),
    USER_NOT_FOUND("User not found"),
    JWT_EXCEPTION("Unable to get JWT Token or JWT Token has expired"),
    EMAIL_SENDING_EXCEPTION("Exception while performing email sending"),
    PASSWORD_NOT_MATCH("Password not match"),

    FILE_LOADING_EXCEPTION("Could not load file"),


    // positive
    LOGGED_SUCCESSFULLY("Logged in Successfully %s"),
    REGISTERED_SUCCESSFULLY("Registered Successfully %s"),
    PASSWORD_RESTORE_MESSAGE("Hello %s, here your new password : %s"),
    PASSWORD_RESTORE_SUBJECT("Password restoration for User"),
    PASSWORD_RESET_FAILED("Password reset failed");

    private String message;

    SportHubConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SportHubConstant{" +
                "message='" + message + '\'' +
                '}';
    }
}
