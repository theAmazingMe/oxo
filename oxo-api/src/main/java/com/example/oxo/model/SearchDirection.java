package com.example.oxo.model;

import org.javatuples.Pair;

public  class SearchDirection {

    private Pair<Integer, Integer> direction;

    public SearchDirection(Integer value0, Integer value1) {
        this.direction = new Pair<>(value0, value1);
    }

    public int getLine() {
        return direction.getValue0();
    }

    public int getColumn() {
        return direction.getValue1();
    }

    public Pair<Integer, Integer> turnBack() {
        this.direction = new Pair<>(direction.getValue0() * -1, direction.getValue1() * -1);
        return direction;
    }
}