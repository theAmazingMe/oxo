package com.example.oxo.resources;

import com.example.oxo.exception.IllegalMoveException;
import com.example.oxo.exception.ResourceNotFoundException;
import com.example.oxo.model.ConclusionDTO;
import com.example.oxo.model.GameRequestDTO;
import com.example.oxo.model.enums.ConclusionType;
import com.example.oxo.service.GameAnalyser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.oxo.model.GameStatusDTO;
import com.example.oxo.service.TicTacToeService;

@RestController
@RequestMapping("api/oxo")
@CrossOrigin(origins = "http://localhost:3000")
public class GameResource {

    @Autowired
    private TicTacToeService ticTacToe;

    @Autowired
    private GameAnalyser gameAnalyser;

    @GetMapping("/{id:[0-9]+}")
    public GameStatusDTO getGameStatus(@PathVariable Integer id){
        return ticTacToe.getStatus(id);
    }

    @PostMapping
    public GameStatusDTO applyAction(@RequestBody GameRequestDTO request){
        Integer id = request.getGameId();

        switch(request.getAction()){
            case RESET_SCORE :
                ticTacToe.resetPlayersScores(id);
                return ticTacToe.startNewGame(id);
            case INIT :
                GameStatusDTO status = ticTacToe.startNewGame();
                return status;
            case PLAY:
                gameAnalyser.placeSymbol(id, request.getMove().getLine(),request.getMove().getColumn());
                return ticTacToe.getStatus(id);
            case REVENGE :
                return ticTacToe.startNewGame(id);
            case SWITCH_TURNS:
                return ticTacToe.switchTurns(id);
        }
        return null;
    }

    ////////////////////////
    // EXCEPTION HANDLING //
    ////////////////////////

    @ExceptionHandler({ RuntimeException.class })
    public GameStatusDTO handleRestException(RuntimeException ex) {
        if(ex instanceof IllegalMoveException){
            ConclusionDTO conclusion = new ConclusionDTO()
                    .setType(ConclusionType.FAULTED)
                    .setMessage(ex.getMessage());

            return new GameStatusDTO().setConclusion(conclusion);

        }else if(ex instanceof ResourceNotFoundException){
            ConclusionDTO conclusion = new ConclusionDTO()
                    .setType(ConclusionType.NON_EXISTENT)
                    .setMessage(String.format("No game could be found"));

            return new GameStatusDTO().setConclusion(conclusion);
        }
        throw new RuntimeException(ex.getMessage());
    }
}