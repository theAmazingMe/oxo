package com.example.oxo.service.ExceptionHandlers.impl;

import com.example.oxo.exception.IllegalMoveException;
import com.example.oxo.exception.ResourceNotFoundException;
import com.example.oxo.model.DTO.ConclusionDTO;
import com.example.oxo.model.DTO.GameStatusDTO;
import com.example.oxo.model.enums.ConclusionType;
import com.example.oxo.service.ExceptionHandlers.RESTExceptionHandler;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class GameExceptionImpl implements RESTExceptionHandler {

    @Override
    public Object onException(Exception ex){
        if(ex instanceof IllegalMoveException){

            return GameStatusDTO.builder().conclusion(ConclusionDTO.builder()
                    .type(ConclusionType.FAULTED)
                    .message(ex.getMessage()))
                    .build();

        }else if(ex instanceof ResourceNotFoundException){

            return GameStatusDTO.builder().conclusion( ConclusionDTO.builder()
                    .type(ConclusionType.NON_EXISTENT)
                    .message(String.format("No game could be found"))).build();
        }
        throw new RuntimeException(ex.getMessage());
    }
}
