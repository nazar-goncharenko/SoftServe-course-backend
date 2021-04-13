package com.softserve.app.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VideoConstant {

    VIDEO_NOT_FOUND("Video not found");

    private final String message;
}
