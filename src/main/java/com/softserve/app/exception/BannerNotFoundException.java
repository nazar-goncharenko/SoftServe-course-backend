package com.softserve.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BannerNotFoundException extends RuntimeException {

    public BannerNotFoundException(String message) {
        super(message);
    }

}