package com.example.oxo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.example.oxo.business.Move;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.example.oxo.business.Player;
import com.example.oxo.model.DTO.ConclusionDTO;
import com.example.oxo.model.DTO.GameStatusDTO;
import com.example.oxo.service.GameAnalyser;

public abstract class AbstractGameAnalyserTest {
	
    protected GameAnalyser gameAnalyser;
    protected TicTacToeServiceImpl game;
    protected GameStatusDTO status;


    @BeforeEach
    @DisplayName("Setup a new game")
    public void startNewGameEachTime(){
    	gameAnalyser = new GameAnalyser();
        game = new TicTacToeServiceImpl();

        game.init();
        setField(gameAnalyser, "totalLines", 3);
        setField(gameAnalyser, "totalColumns", 3);
        setField(gameAnalyser, "goal", 3);
        setField(gameAnalyser, "ticTacToe", game);
        
        setField(game, "lines", 3);
        setField(game, "cols", 3);
        
        Player p1 = new Player().setPseudo("John Do");
        Player p2 = new Player().setPseudo("John Smith");

        List<Player> players = Arrays.asList(p1,p2);

        status = game.startNewGame();
        game.initializePlayers(status.getGameId(), players);

        assertEquals(0, p1.getWins());
        assertEquals(0, p2.getWins());
        assertEquals(0, p1.getLoss());
        assertEquals(0, p2.getLoss());
    }

    protected ConclusionDTO playADraw(){
        int[][] positions = {
                {0,0},{1,0},{2,0}, //    X | O | X
                {0,2},{1,1},{2,2}, // => O | X | X
                {1,2},{0,1},{2,1}  //    O | X | O
        };

        return playAGame(positions);
    }


    protected ConclusionDTO playAGame(int[][] positions){

        ConclusionDTO conclusion = null;

        for (int[] position : positions ) {
            conclusion = gameAnalyser.placeSymbol(status.getGameId(), Move.moveFromArray(position));
        }

        return conclusion;
    }
    
    protected void setField(Object instance, String name, Object value) {
    	try {
    		Field field = instance.getClass().getDeclaredField(name);
    		field.setAccessible(true);
    		field.set(instance, value);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
    }
}
