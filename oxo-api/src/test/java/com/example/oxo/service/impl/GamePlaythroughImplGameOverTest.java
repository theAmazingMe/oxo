package com.example.oxo.service.impl;

import com.example.oxo.business.Player;
import com.example.oxo.exception.IllegalMoveException;
import com.example.oxo.model.ConclusionDTO;
import com.example.oxo.model.enums.ConclusionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Game concluded")
class GamePlaythroughImplGameOverTest extends AbstractGameAnalyserTest {

    @Test
    @DisplayName("a draw is specified at the end of a draw")
    void test0() {
        ConclusionDTO conclusion = this.playADraw();
        assertEquals(conclusion.getType(), ConclusionType.DRAW);
    }

    @Test
    @DisplayName("A game can be won horizontally")
    void test1() {
        ConclusionDTO conclusion = this.playAGame(new int[][]{
                {0,0},{1,0},{0,1},  //      X | X | X
                {1,1},{0,2}         // =>   O | O | .
                                    //      . | . | .
        });

        assertThat(conclusion.getMessage(),matchesPattern(Pattern.compile(".*X.*aligned.*",CASE_INSENSITIVE)));
        assertThat(conclusion.getMessage(),matchesPattern(Pattern.compile(".*horizontal.*",CASE_INSENSITIVE)));
        assertEquals(conclusion.getType(), ConclusionType.FINISHED);
    }

    @Test
    @DisplayName("A game can be won vertically")
    void test2() {
        ConclusionDTO conclusion = this.playAGame(new int[][]{
                {0,1},{0,2},{1,1},  //      . | X | O
                {1,2},{2,1}         // =>   . | X | O
                                    //      . | X | .
        });

        assertThat(conclusion.getMessage(),matchesPattern(Pattern.compile(".*vertical.*",CASE_INSENSITIVE)));
        assertEquals(conclusion.getType(), ConclusionType.FINISHED);
    }

    @Test
    @DisplayName("A game can be won on the ascending diagonal")
    void test3() {
        ConclusionDTO conclusion = this.playAGame(new int[][]{
                {1,0},{2,0},{2,1},  //      . | X | O
                {1,1},{1,2},{0,2}   // =>   . | O | X
                                    //      O | X | .
        });

        assertThat(conclusion.getMessage(),matchesPattern(Pattern.compile(".*O.*aligned.*asc.*diag.*",CASE_INSENSITIVE)));
        assertEquals(conclusion.getType(), ConclusionType.FINISHED);
    }

    @Test
    @DisplayName("A game can be won on the descending diagonal, placing the last in the center")
    void test4() {
        ConclusionDTO conclusion = this.playAGame(new int[][]{
                {1,0},{0,0},{2,1},  //      O | X | .
                {2,2},{1,2},{1,1}   // =>   . | O | X
                                    //      . | X | O
        });

        assertThat(conclusion.getMessage(),matchesPattern(Pattern.compile(".*O.*aligned.*desc.*diag.*",CASE_INSENSITIVE)));
        assertEquals(conclusion.getType(), ConclusionType.FINISHED);
    }

    @Test
    @DisplayName("A game can be won on the descending diagonal, placing the last in the SE corner")
    void test5() {
        ConclusionDTO conclusion = this.playAGame(new int[][]{
                {1,0},{1,1},{2,1},  //      O | X | .
                {0,0},{1,2},{2,2}   // =>   . | O | X
                                    //      . | X | O
        });

        assertThat(conclusion.getMessage(),matchesPattern(Pattern.compile(".*O.*aligned.*desc.*diag.*",CASE_INSENSITIVE)));
        assertEquals(conclusion.getType(), ConclusionType.FINISHED);
    }

    @Test
    @DisplayName("A game can be won on the descending diagonal, placing the last in the NE corner")
    void test6() {
        ConclusionDTO conclusion = this.playAGame(new int[][]{
                {1,0},{1,1},{2,1},  //      O | X | .
                {2,2},{1,2},{0,0}   // =>   . | O | X
                                    //      . | X | O
        });

        assertThat(conclusion.getMessage(),matchesPattern(Pattern.compile(".*O.*aligned.*desc.*diag.*",CASE_INSENSITIVE)));
        assertEquals(conclusion.getType(), ConclusionType.FINISHED);
    }

    @Test
    @DisplayName("The winner should have his count of victories increment. But the loser his/her loss count")
    void test7() {
        test6();

        List<Player> players = status.getPlayers();

        assertEquals(players.get(0).getLoss() , 1);
        assertEquals(players.get(1).getWins() , 1);
    }

    @Test
    @DisplayName("Play after a game is over should not be allowed")
    void test8() {
        Exception exception = assertThrows(IllegalMoveException.class, () -> {

            test6();
            gameAnalyser.placeSymbol(status.getGameId(),2,0);

        });

        assertThat(
                exception.getMessage(),
                matchesPattern(Pattern.compile(".*game over .*no move.*",CASE_INSENSITIVE))
        );
    }
}