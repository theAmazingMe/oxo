package com.example.oxo.business;

import lombok.Getter;

@Getter
public class Move {

    public Move(int [] move){
        this.line = move[0];
        this.column = move[1];
    }

    private final int column;
    private final int line;

    public static Move moveFromArray(int[] move){
        return new Move(move);
    }
    public static Move moveFrom(int line, int column){
        return new Move(new int[]{line,column});
    }
}
