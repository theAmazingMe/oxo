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
            ConclusionDTO conclusion = new ConclusionDTO()
                    .setType(ConclusionType.FAULTED)
                    .setMessage(ex.getMessage());

            return new GameStatusDTO().setConclusion(conclusion);

        }else if(ex instanceof ResourceNotFoundException){
            ConclusionDTO conclusion = new ConclusionDTO()
                    .setType(ConclusionType.NON_EXISTENT)
                    .setMessage(String.format("No game could be found"));

            return new GameStatusDTO().setConclusion(conclusion);
        }
        throw new RuntimeException(ex.getMessage());
    }
}
