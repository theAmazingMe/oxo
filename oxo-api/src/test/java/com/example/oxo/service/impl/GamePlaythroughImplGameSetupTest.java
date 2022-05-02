package com.example.oxo.service.impl;

import com.example.oxo.business.Player;

import com.example.oxo.exception.PseudoConflictException;
import com.example.oxo.exception.TooFewPseudoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Game setup")
class GameAnalyserGameSetupTest extends AbstractGameAnalyserTest{

    @Test
    @DisplayName("A game cannot start without two players")
    void test1(){
        Exception exception = assertThrows(TooFewPseudoException.class, () -> {
        	game.startNewGame();
        	game.initializePlayers(status.getGameId(), null);
        });
        Exception exception2 = assertThrows(TooFewPseudoException.class, () -> {
        	game.startNewGame();
        	game.initializePlayers(status.getGameId(), new ArrayList<>());

        });
        Exception exception3 = assertThrows(TooFewPseudoException.class, () -> {
            Player singlePlayer = new Player().setPseudo("Rémi sans famille");
            List<Player> pool = new ArrayList<>();
            pool.add(singlePlayer);

            game.startNewGame();
        	game.initializePlayers(status.getGameId(), pool);
        });
        String message1 = exception.getMessage();
        String message2 = exception2.getMessage();
        String message3 = exception3.getMessage();

        assertEquals(message1,message2);
        assertThat(message1,matchesPattern("Two players have to be set.*"));

        // the message is different but the exception is the same
        assertThat(message3,matchesPattern("Two players have to be set.*"));
    }

    @Test
    @DisplayName("A game cannot start with two players of same pseudos")
    void test2(){
        Exception exception = assertThrows(PseudoConflictException.class, () -> {
            Player p1 = new Player().setPseudo("John Do");
            Player p2 = new Player().setPseudo("John Do");

            game.initializePlayers(status.getGameId(),Arrays.asList(p1,p2));
            game.startNewGame();
        });
        assertThat(exception.getMessage(),matchesPattern(".*same pseudo.*"));
    }
}