package com.example.oxo.model.DTO;

import com.example.oxo.model.enums.GameAction;
import lombok.Data;

@Data
public class GameRequestDTO {
    private Integer gameId;
    private GameAction action;
    private MoveDTO move;
}
