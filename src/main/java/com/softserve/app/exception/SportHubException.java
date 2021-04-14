package com.softserve.app.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SportHubException extends RuntimeException{
    private int code;

    public SportHubException(String message, int code){
        super(message);
        this.code = code;
    }
}
