package com.example.oxo.service.impl;

import com.example.oxo.exception.IllegalMoveException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.example.oxo.business.Move.moveFrom;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("gameAnalyser")
public class GameAnalysergameAnalyserTest extends AbstractGameAnalyserTest {

    @Test
    @DisplayName("The first symbol to be placed is X")
    void test1() {
        gameAnalyser.placeSymbol(status.getGameId(), moveFrom(0,0));
        Character placedSymbol = status.getGrid()[0][0];

        assertEquals(1, status.getTurnCount());
        assertEquals('X',placedSymbol);
    }

    @Test
    @DisplayName("O is placed after X is placed")
    void test2() {
        gameAnalyser.placeSymbol(status.getGameId(),moveFrom(0,0));
        gameAnalyser.placeSymbol(status.getGameId(),moveFrom(1,0));
        char placedSymbol = status.getGrid()[1][0];

        assertEquals(placedSymbol,'O');
    }

    @Test
    @DisplayName("It is impossible to play at a same position")
    void test3() {
        assertThrows(IllegalMoveException.class, () -> {
            gameAnalyser.placeSymbol(status.getGameId(),moveFrom(0,0));
            gameAnalyser.placeSymbol(status.getGameId(),moveFrom(0,0));
        });
    }

    @Test
    @DisplayName("It is impossible to play once the grid is full")
    void test4() {
        Exception exception = assertThrows(IllegalMoveException.class, () -> {

            playADraw();

            // the grid is already filled
            gameAnalyser.placeSymbol(status.getGameId(),moveFrom(0,1));
        });

        assertThat(
                exception.getMessage(),
                matchesPattern(Pattern.compile(".*game over .* 9 turns.*",CASE_INSENSITIVE))
        );
    }
}