package com.example.oxo.service.helpers;

import com.example.oxo.business.Move;
import com.example.oxo.exception.IllegalMoveException;
import com.example.oxo.model.DTO.GameStatusDTO;

import static com.example.oxo.model.enums.ConclusionType.FINISHED;

public final class CustomMoveValidator {
    public static void validatePlacement(final GameStatusDTO gameStatus, int turns, Move move) {

        int line = move.getLine();
        int col = move.getColumn();
        
        Character symbol;

        if (gameStatus.getConclusion().getType() == FINISHED && turns > 0) {
            throw new IllegalMoveException(String.format(
                    "Impossible move. Game over reached. no move should be allowed after the game is won", turns));
        }

        if (turns > 8) {
            throw new IllegalMoveException(
                    String.format("Impossible move. Game over reached with: %d turns", turns));

        } else if ((symbol = gameStatus.getGrid()[line][col]) != null) {
            // when a not empty cell is targeted
            throw new IllegalMoveException("Impossible move. Position already used. Symbol:" + symbol);
        }
    }
}
