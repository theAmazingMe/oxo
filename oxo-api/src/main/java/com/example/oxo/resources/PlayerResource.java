package com.example.oxo.resources;

import com.example.oxo.model.DTO.CreatePlayersDTO;
import com.example.oxo.model.DTO.GameStatusDTO;
import com.example.oxo.service.ExceptionHandlers.RESTExceptionHandler;
import com.example.oxo.service.PlayerService;
import com.example.oxo.service.TicTacToeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/oxo/players")
@CrossOrigin(origins = "*")
public class PlayerResource {

    private final PlayerService playerService;

    private final TicTacToeService ticTacToeService;

    @Qualifier("playerExceptions")
    private final RESTExceptionHandler exceptionHandler;

    @Autowired
    public PlayerResource (PlayerService playerService, TicTacToeService ticTacToeService, RESTExceptionHandler exceptionHandler){
        this.playerService = playerService;
        this.ticTacToeService = ticTacToeService;
        this.exceptionHandler = exceptionHandler;
    }

    @PostMapping
    public GameStatusDTO createPlayersDTO(@RequestBody CreatePlayersDTO players){

        return ticTacToeService.initializePlayers(players.getGameId(), playerService.createPlayers(players));
    }

    @ExceptionHandler({ RuntimeException.class })
    public GameStatusDTO handleRestException(Exception ex) {
        return (GameStatusDTO)exceptionHandler.onException(ex);
    }
}
