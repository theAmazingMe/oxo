package com.example.oxo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class PseudoConflictException extends RuntimeException{

    public PseudoConflictException(String message) {
        super(message);
    }
}
