package com.example.oxo.service.impl;

import static com.example.oxo.model.enums.ConclusionType.ONGOING;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import com.example.oxo.exception.PseudoConflictException;
import com.example.oxo.exception.TooFewPseudoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.oxo.business.Player;
import com.example.oxo.exception.ResourceNotFoundException;
import com.example.oxo.model.ConclusionDTO;
import com.example.oxo.model.GameStatusDTO;
import com.example.oxo.service.TicTacToeService;

@Service
public class TicTacToeServiceImpl implements TicTacToeService{
	
	private Map<Integer, GameStatusDTO> storedGames;
	
	private AtomicInteger gameIdStore = new AtomicInteger(1);
	
	@Value("${game.dimension:3}")
	private int lines; 
	@Value("${game.dimension:3}")
	private int cols;


    @PostConstruct
    public void init() {
    	storedGames = new HashMap<>();
    }

    @Override
    public GameStatusDTO startNewGame() {
    	 ConclusionDTO actualConclusion = new ConclusionDTO().setType(ONGOING)
         		.setMessage("New game started");
    	int id=gameIdStore.incrementAndGet();
    	GameStatusDTO status = new GameStatusDTO()
        		.setGameId(id)
        		.setConclusion(actualConclusion)
        		.setTurnCount(0)
        		.setGrid(new Character[lines][cols]);
    	
    	storedGames.put(id, status);
    	return status;
    }
    @Override
    public GameStatusDTO startNewGame(Integer id) {

    	GameStatusDTO status = storedGames.get(id);
        
    	int totalPlayers = status.getPlayers() != null ? status.getPlayers().size():0;
        if(totalPlayers != 2){
            throw new IllegalArgumentException("Two players have to be set to play. Actual: "+totalPlayers);
        }
        
        ConclusionDTO actualConclusion = new ConclusionDTO().setType(ONGOING)
        		.setMessage("New game started");

        status = new GameStatusDTO()
        		.setGameId(id)
        		.setConclusion(actualConclusion)
        		.setTurnCount(0)
                .setPlayers(status.getPlayers())
        		.setGrid(new Character[lines][cols]);

        storedGames.put(id,status);
        return status;
    }
    @Override
    public GameStatusDTO initializePlayers(Integer gameId, List<Player> players) {
    	if(players == null || players.size() != 2) {        	
        	throw new TooFewPseudoException("Two players have to be set. currently " +
        				(players == null ? 0 : players.size()));
        }
        
        if(players.get(0).getPseudo().equals(players.get(1).getPseudo())) {
            throw new PseudoConflictException("The two players have the same pseudo: "+players.get(0).getPseudo());
        }
        getStatus(gameId).setPlayers(players);
        return getStatus(gameId);
    }

    @Override
    public GameStatusDTO resetPlayersScores(Integer id) {
    	GameStatusDTO status = getStatus(id);
        for(Player p : status.getPlayers()){
            p.setWins(0);
            p.setLoss(0);
        }

        status = new GameStatusDTO()
                .setGameId(id)
                .setConclusion(status.getConclusion())
                .setTurnCount(0)
                .setPlayers(status.getPlayers())
                .setGrid(new Character[lines][cols]);

        storedGames.put(id,status);
        return status;
    }
    @Override
    public GameStatusDTO getStatus(Integer id) {
		GameStatusDTO status = storedGames.get(id);
		if(status == null) {
			throw new ResourceNotFoundException(String.format("No game found for the specified id [%d]",id));
		}
		return status;
	}

    @Override
    public GameStatusDTO switchTurns(Integer id) {
        GameStatusDTO status = storedGames.get(id);

        // the stored players list is immutable

        List<Player> players = new ArrayList<>(storedGames.get(id).getPlayers());
        Collections.reverse(players);

        status = new GameStatusDTO()
                .setGameId(id)
                .setConclusion(status.getConclusion())
                .setTurnCount(0)
                .setPlayers(players)
                .setGrid(new Character[lines][cols]);

        storedGames.put(id,status);
        return status;
    }
}
