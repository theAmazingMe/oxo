package com.example.oxo.service.impl;

import com.example.oxo.business.Player;
import com.example.oxo.exception.ResourceNotFoundException;
import com.example.oxo.model.DTO.ConclusionDTO;
import com.example.oxo.model.DTO.GameStatusDTO;
import com.example.oxo.service.PlayerService;
import com.example.oxo.service.TicTacToeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.oxo.model.enums.ConclusionType.ONGOING;

@Service
public class TicTacToeServiceImpl implements TicTacToeService{
	
	private Map<Integer, GameStatusDTO> storedGames;
	
	private final AtomicInteger gameIdStore = new AtomicInteger(1);

    private final PlayerService playerService;
	
	@Value("${game.dimension:3}")
	private int lines; 
	@Value("${game.dimension:3}")
	private int cols;

    @Autowired
    TicTacToeServiceImpl(PlayerService playerService){
        this.playerService = playerService;
    }


    @PostConstruct
    public void init() {
        gameIdStore.set(0);
    	storedGames = new HashMap<>();
    }

    @Override
    public GameStatusDTO startNewGame() {
    	int id=gameIdStore.incrementAndGet();
    	GameStatusDTO status = buildNewGame(id);
    	
    	storedGames.put(id, status);
    	return status;
    }

    private GameStatusDTO buildNewGame(int id) {
        return GameStatusDTO.builder()
                .gameId(id)
                .conclusion(concludeGameStarted())
                .turnCount(0)
                .grid(new Character[lines][cols])
                .build();
    }

    private ConclusionDTO concludeGameStarted(){
        return ConclusionDTO.builder()
                .type(ONGOING)
                .message("New game started")
                .build();
    }

    @Override
    public GameStatusDTO startNewGame(Integer id) {

    	GameStatusDTO status = storedGames.get(id);

    	playerService.checkPlayersToCreate(status.getPlayers());

        GameStatusDTO statusWithGame = buildAGame(id,status,concludeGameStarted());

        storedGames.put(id,statusWithGame);
        return statusWithGame;
    }
    @Override
    public GameStatusDTO initializePlayers(Integer gameId, List<Player> players) {
        playerService.checkPlayersToCreate(playerService.getPseudos(players));
        getStatus(gameId).setPlayers(players);
        return getStatus(gameId);
    }

    private GameStatusDTO buildAGame(Integer gameId, GameStatusDTO status, ConclusionDTO conclusion){
        return GameStatusDTO.builder()
                .gameId(gameId)
                .conclusion(conclusion)
                .turnCount(0)
                .players(status.getPlayers())
                .grid(new Character[lines][cols])
                .build();
    }

    @Override
    public GameStatusDTO resetPlayersScores(Integer id) {
    	GameStatusDTO status = getStatus(id);
        for(Player p : status.getPlayers()){
            p.setWins(0);
            p.setLoss(0);
        }

        status = buildAGame(id,status,status.getConclusion());

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

        Collections.reverse(storedGames.get(id).getPlayers());

        GameStatusDTO alteredStatus = buildAGame(
                id,
                status,
                status.getConclusion()
        );

        storedGames.put(id,alteredStatus);
        return alteredStatus;
    }
}
