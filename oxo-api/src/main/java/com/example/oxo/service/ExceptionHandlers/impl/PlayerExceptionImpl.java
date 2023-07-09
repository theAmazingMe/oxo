package com.example.oxo.service.ExceptionHandlers.impl;

import com.example.oxo.exception.PseudoConflictException;
import com.example.oxo.exception.TooFewPseudoException;
import com.example.oxo.model.DTO.ConclusionDTO;
import com.example.oxo.model.DTO.GameStatusDTO;
import com.example.oxo.model.enums.ConclusionType;
import com.example.oxo.service.ExceptionHandlers.RESTExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("playerExceptions")
public class PlayerExceptionImpl implements RESTExceptionHandler {

    @Override
    public Object onException(Exception ex) {
        if(ex instanceof PseudoConflictException || ex instanceof TooFewPseudoException){

            return GameStatusDTO.builder().conclusion(ConclusionDTO.builder()
                    .type(ConclusionType.INVALID)
                    .message(ex.getMessage()))
                    .build();
        }
        throw new RuntimeException(ex.getMessage());
    }
}
