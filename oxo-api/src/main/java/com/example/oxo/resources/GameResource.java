package com.example.oxo.resources;

import com.example.oxo.business.Move;
import com.example.oxo.model.DTO.GameRequestDTO;
import com.example.oxo.service.ExceptionHandlers.RESTExceptionHandler;
import com.example.oxo.service.GameAnalyser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.oxo.model.DTO.GameStatusDTO;
import com.example.oxo.service.TicTacToeService;

@RestController
@RequestMapping("api/oxo")
@CrossOrigin(origins = "*")
public class GameResource {

    @Autowired
    private TicTacToeService ticTacToe;

    @Autowired
    private GameAnalyser gameAnalyser;

    @Autowired
    private RESTExceptionHandler exceptionHandler;

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
                int line = request.getMove().getLine();
                int column = request.getMove().getColumn();
                Move move = new Move(new int[]{line,column});
                gameAnalyser.placeSymbol(id, move);
                return ticTacToe.getStatus(id);
            case REVENGE :
                return ticTacToe.startNewGame(id);
            case SWITCH_TURNS:
                return ticTacToe.switchTurns(id);
        }
        return null;
    }

    @ExceptionHandler({ RuntimeException.class })
    public GameStatusDTO handleRestException(RuntimeException ex) {
        return (GameStatusDTO)exceptionHandler.onException(ex);
    }
}