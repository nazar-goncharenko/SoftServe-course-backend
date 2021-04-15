package com.softserve.app.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
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
    PASSWORD_RESET_FAILED("Password reset failed"),

    ARTICLE_CREATED_SUCCESSFULLY("Article was created successfully"),
    ARTICLE_UPDATED_SUCCESSFULLY("Article was updated successfully"),
    ARTICLE_DELETED_SUCCESSFULLY("Article was deleted successfully"),
    ARTICLE_IMAGE_UPDATED_SUCCESSFULLY("Article image was updated successfully"),
    ARTICLE_NOT_FOUND("Article was not found"),


    BANNER_CREATED_SUCCESSFULLY("Banner was created successfully"),
    BANNER_UPDATED_SUCCESSFULLY("Banner was updated successfully"),
    BANNER_CONFIGURED_SUCCESSFULLY("Banner was configured successfully"),
    BANNER_DELETED_SUCCESSFULLY("Banner was deleted successfully"),
    BANNER_NOT_FOUND("Banner doesn't exist"),
    BANNER_HIDDEN_SUCCESSFULLY("Banners of this category are not visible to users anymore"),
    BANNER_SHOWN_SUCCESSFULLY("Banners of this category are visible to the users now"),






    FILES_NOT_IMAGE("Uploaded file isn't an image"),
    FILES_IMAGE_IS_NOT_UPLOADED("Can not upload the image");

    private final String message;
}
