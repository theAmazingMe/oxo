package com.example.oxo.model.enums;

public enum Direction {

    HORIZONTAL("horizontal", new int[]{0, 1}),                       // => |
    VERTICAL("vertical", new int[]{-1, 0}),                   // => -
    DESCENDANT_DIAGONAL("descendant diagonal", new int[]{1, 1}),  // => /
    ASCENDANT_DIAGONAL("ascendant diagonal", new int[]{1, -1}); // => \

    private int[] matrix;
    private String name;

    Direction(String name, int[] matrix) {
        this.name = name;
        this.matrix = matrix;
    }

    public int[] getMatrix() {
        return matrix;
    }

    public String getName() {
        return name;
    }
}
