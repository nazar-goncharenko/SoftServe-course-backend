package com.softserve.app.constant;

public enum BannerConstant {

    CREATED_SUCCESSFULLY("Banner was created successfully"),
    UPDATED_SUCCESSFULLY("Banner was updated successfully"),
    CONFIGURED_SUCCESSFULLY("Banner was configured successfully"),
    DELETED_SUCCESSFULLY("Banner was deleted successfully"),
    BANNER_NOT_FOUND("Banner doesn't exist"),
    NOT_IMAGE("Uploaded file isn't an image"),
    HIDDEN_SUCCESSFULLY("Banners of this category are not visible to users anymore"),
    SHOWN_SUCCESSFULLY("Banners of this category are visible to the users now");

    private final String message;

    BannerConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}