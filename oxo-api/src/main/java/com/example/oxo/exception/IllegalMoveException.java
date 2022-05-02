package com.example.oxo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class IllegalMoveException extends RuntimeException{
    public IllegalMoveException(String message) {
        super(message);
    }
}
