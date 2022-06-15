package com.example.oxo.service.helpers;

import com.example.oxo.business.Move;
import com.example.oxo.model.DTO.GameStatusDTO;
import lombok.Getter;

@Getter
public class AlignmentStepper {

    private int endDirectionFlag = 0;
    private int score = 1;
    private int step = resetStep();
    private final int goal;

    private final Character symbol;
    private final Move move;
    private final GameStatusDTO gameStatus;

    public AlignmentStepper(GameStatusDTO gameStatus, Move move, char symbol, int goal) {
        this.gameStatus = gameStatus;
        this.move = move;
        this.symbol = symbol;
        this.goal = goal;
    }

    public Character getSymbole(int line, int column) {
        try {
            return gameStatus.getGrid()[line][column];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    public int stepUp() {
        return ++step;
    }

    public int scoreUp() {
        return ++score;
    }

    public int resetStep() {
        return step = 1;
    }

    public int flagTheDirection() {
        return ++endDirectionFlag;
    }
}
