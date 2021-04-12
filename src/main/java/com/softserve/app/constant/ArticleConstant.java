package com.softserve.app.constant;

public enum ArticleConstant {

    CREATED_SUCCESSFULLY("Article was created successfully"),
    UPDATED_SUCCESSFULLY("Article was updated successfully"),
    DELETED_SUCCESSFULLY("Article was updated successfully"),
    ARTICLE_NOT_FOUND("Article was not found");

    private final String message;

    ArticleConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
