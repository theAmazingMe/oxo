package com.example.oxo.resources;

import com.example.oxo.model.DTO.GameStatusDTO;
import com.example.oxo.service.ExceptionHandlers.RESTExceptionHandler;
import com.example.oxo.service.ExceptionHandlers.impl.PlayerExceptionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.example.oxo.model.DTO.CreatePlayersDTO;
import com.example.oxo.service.PlayerService;

@RestController
@RequestMapping("api/oxo/players")
@CrossOrigin(origins = "*")
public class PlayerResource {

    @Autowired
    private PlayerService playerService;

    @Autowired
    @Qualifier("playerExceptions")
    private RESTExceptionHandler exceptionHandler;

    @PostMapping
    public GameStatusDTO createPlayersDTO(@RequestBody CreatePlayersDTO players){
        return playerService.createPlayers(players);
    }

    @ExceptionHandler({ RuntimeException.class })
    public GameStatusDTO handleRestException(Exception ex) {
        return (GameStatusDTO)exceptionHandler.onException(ex);
    }
}
