package com.example.oxo.service.impl;

import com.example.oxo.business.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Game setup")
class GameAnalyserGameSetupTest extends AbstractGameAnalyserTest{



    @ParameterizedTest(name = "when {1}")
    @DisplayName("A game cannot start without two players")
    @MethodSource
    void test1(Supplier supplier,String ignoreDescription){
        assertThrows(IllegalArgumentException.class, () -> {
        	game.startNewGame();
        	game.initializePlayers(status.getGameId(), supplier.supplyList());
        });
    }

    private static List<Player> getSinglePlayer(){
        Player singlePlayer = Player.builder().pseudo("Rémi sans famille").build();
        List<Player> pool = new ArrayList<>();
        pool.add(singlePlayer);
        return pool;
    }

    @FunctionalInterface
    interface Supplier{
        List<Player> supplyList();
    }

    private static Stream<Arguments> test1(){
        Supplier singlePlayer = GameAnalyserGameSetupTest::getSinglePlayer;
        Supplier nullList = () -> null;
        Supplier emptyList = ArrayList::new;
        return Stream.of(
                Arguments.of(singlePlayer,"There is one player only"),
                Arguments.of(nullList, "The list of player does not exist"),
                Arguments.of(emptyList,"The list of player is empty")
        );
    }

    @Test
    @DisplayName("A game cannot start with two players of same pseudos")
    void test2(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Player p1 = Player.builder().pseudo("John Do").build();
            Player p2 = Player.builder().pseudo("John Do").build();

            game.initializePlayers(status.getGameId(),Arrays.asList(p1,p2));
            game.startNewGame();
        });
    }
}