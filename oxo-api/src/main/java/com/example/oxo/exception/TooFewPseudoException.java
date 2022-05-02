package com.example.oxo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class TooFewPseudoException extends RuntimeException{

    public TooFewPseudoException(String message) {
        super(message);
    }
}
