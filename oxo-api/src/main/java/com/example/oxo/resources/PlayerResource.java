package com.example.oxo.resources;

import com.example.oxo.exception.IllegalMoveException;
import com.example.oxo.exception.PseudoConflictException;
import com.example.oxo.exception.ResourceNotFoundException;
import com.example.oxo.exception.TooFewPseudoException;
import com.example.oxo.model.ConclusionDTO;
import com.example.oxo.model.GameStatusDTO;
import com.example.oxo.model.enums.ConclusionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.oxo.model.CreatePlayersDTO;
import com.example.oxo.service.PlayerService;

@RestController
@RequestMapping("api/oxo/players")
@CrossOrigin(origins = "http://localhost:3000")
public class PlayerResource {

    @Autowired
    private PlayerService playerService;

    @PostMapping
    public GameStatusDTO createPlayersDTO(@RequestBody CreatePlayersDTO players){
        return playerService.createPlayers(players);
    }

    @ExceptionHandler({ RuntimeException.class })
    public GameStatusDTO handleRestException(Exception ex) {
        if(ex instanceof PseudoConflictException || ex instanceof TooFewPseudoException){
            ConclusionDTO conclusion = new ConclusionDTO()
                    .setType(ConclusionType.INVALID)
                    .setMessage(ex.getMessage());

            return new GameStatusDTO().setConclusion(conclusion);
        }
        throw new RuntimeException(ex.getMessage());
    }
}
