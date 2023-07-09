package com.example.oxo.service;

import com.example.oxo.business.Move;
import com.example.oxo.business.Player;
import com.example.oxo.model.DTO.ConclusionDTO;
import com.example.oxo.model.DTO.GameStatusDTO;
import com.example.oxo.model.SearchDirection;
import com.example.oxo.model.enums.Direction;
import com.example.oxo.service.helpers.AlignmentStepper;
import com.example.oxo.service.helpers.GridSearchAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.example.oxo.model.enums.ConclusionType.*;
import static com.example.oxo.model.enums.Direction.*;
import static com.example.oxo.service.helpers.CustomMoveValidator.validatePlacement;

@Service
public class GameAnalyser {

    private static final Character[] SYMBOLS = new Character[]{'X', 'O'};

    @Value("${game.dimension:3}")
    private int totalLines;
    @Value("${game.dimension:3}")
    private int totalColumns;
    @Value("${game.occurrence.length:3}")
    private int goal; // the number of occurrence of a symbol in a row to reach to win

    @Autowired
    private TicTacToeService ticTacToe;

    public ConclusionDTO placeSymbol(Integer gameId, Move move) {
        GameStatusDTO gameStatus = ticTacToe.getStatus(gameId);
        int turns = gameStatus.getTurnCount();
        int iSymbol = turns % SYMBOLS.length;
        char playedSymbol = SYMBOLS[iSymbol]; // X is odd, O is even (for two different symbols)

        validatePlacement(gameStatus, turns, move);

        gameStatus.getGrid()[move.getLine()][move.getColumn()] = playedSymbol; // the symbol of the player

        // try to conclude that we have a winner
        ConclusionDTO conclusion = concludeAlignment(gameStatus, move, playedSymbol);

        // the symbol is placed so we count up the turn
        gameStatus.setTurnCount(gameStatus.getTurnCount() + 1);

        gameStatus.setConclusion(conclusion);
        return onConclusion(gameStatus, conclusion, turns);
    }

    private ConclusionDTO concludeAlignment(GameStatusDTO gameStatus, Move move, char symbol) {

        final Direction[] directions = {HORIZONTAL, VERTICAL, ASCENDANT_DIAGONAL, DESCENDANT_DIAGONAL};

        for (Direction direction : directions) {

            int lineMovement = direction.getMatrix()[0];
            int columnMovement = direction.getMatrix()[1];

            SearchDirection searchDirection = new SearchDirection(lineMovement, columnMovement);
            AlignmentStepper stepper = new AlignmentStepper(gameStatus, move, symbol, goal);

            // is the last move is a winning move ?
            GridSearchAlgorithm algorithm = new GridSearchAlgorithm();
            boolean winning = algorithm.directNeighbourSearch(searchDirection, stepper);

            if (winning) {
                return notifyWin(direction,symbol);

            } else if (gameStatus.getTurnCount() == 8) {
                return notifyNoWinner();
            }
        }

        return ConclusionDTO.builder().type(ONGOING)
                .message("No player wins").build();
    }

    private ConclusionDTO notifyNoWinner() {
        return ConclusionDTO.builder().type(DRAW).message("This is a draw").build();
    }

    private ConclusionDTO notifyWin(Direction direction, char symbol) {
        String message = String.format("%c's aligned on the %s",
                symbol, direction.getName()
        );

        // it is GAME OVER
        return ConclusionDTO.builder().type(FINISHED)
                .message(message).build();
    }

    private ConclusionDTO onConclusion(GameStatusDTO gameStatus, ConclusionDTO conclusion, int turns) {
        if (conclusion.getType() == FINISHED) {
            // We have a winner

            Player victorious = gameStatus.getPlayers().get(turns % 2);
            victorious.victory();

            Player defeated = gameStatus.getPlayers().get((turns + 1) % 2);
            defeated.defeat();
        }

        // The game is still ongoing
        return conclusion;
    }
}
