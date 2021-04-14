package com.softserve.app.exception;

import com.softserve.app.models.SportHubExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class SportHubExceptionHandler {

    @ExceptionHandler(SportHubException.class)
    protected ResponseEntity<SportHubExceptionResponse> handleSportHubException(SportHubException ex){
        SportHubExceptionResponse response = new SportHubExceptionResponse(ex.getCode(), ex.getMessage(), Instant.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
