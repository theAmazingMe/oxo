package com.example.oxo.service;

import static com.example.oxo.model.enums.ConclusionType.DRAW;
import static com.example.oxo.model.enums.ConclusionType.FINISHED;
import static com.example.oxo.model.enums.ConclusionType.ONGOING;
import static com.example.oxo.model.enums.Direction.ASCENDANT_DIAGONAL;
import static com.example.oxo.model.enums.Direction.DESCENDANT_DIAGONAL;
import static com.example.oxo.model.enums.Direction.HORIZONTAL;
import static com.example.oxo.model.enums.Direction.VERTICAL;

import com.example.oxo.exception.IllegalMoveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.oxo.business.Player;
import com.example.oxo.model.ConclusionDTO;
import com.example.oxo.model.GameStatusDTO;
import com.example.oxo.model.enums.Direction;

@Service
public class GameAnalyser  {

	private static final Character[] SYMBOLS = new Character[] { 'X', 'O' };

	@Value("${game.dimension:3}")
	private int totalLines; 
	@Value("${game.dimension:3}")
	private int totalColumns;
	@Value("${game.occurrence.length:3}")
	private int goal; // the number of occurrence of a symbol in a row to reach to win

	@Autowired
	private TicTacToeService ticTacToe;

    private static class AlignmentStepper {
        int endDirectionFlag = 0;
        int score = 1;
        int step = 1;
        int waySwitch = 1;
    }

	public ConclusionDTO placeSymbol(Integer gameId, int line, int col) {
		GameStatusDTO gameStatus = ticTacToe.getStatus(gameId);
		int turns = gameStatus.getTurnCount();
		int iSymbol = turns % SYMBOLS.length;
		char playedSymbol = SYMBOLS[iSymbol]; // X is odd, O is even (for two different symbols)

		checkIfPlacementValid(gameStatus, turns, line, col);

		gameStatus.getGrid()[line][col] = playedSymbol; // the symbol of the player

		// try to conclude that we have a winner
		ConclusionDTO conclusion = lookAroundPoint(gameStatus, new int[] { line, col }, playedSymbol);

		// the symbol is placed so we count up the turn
		gameStatus.setTurnCount(gameStatus.getTurnCount() + 1);

		gameStatus.setConclusion(conclusion);
		return onConclusion(gameStatus, conclusion, turns);
	}

    private ConclusionDTO lookAroundPoint(GameStatusDTO gameStatus, int[] point, char symbol) {

        final Direction[] directions = {HORIZONTAL, VERTICAL, ASCENDANT_DIAGONAL, DESCENDANT_DIAGONAL};

        for (Direction direction : directions) {

            // is the last move is a winning move ?
            boolean winning = lookAroundPoint(gameStatus, direction.getMatrix(), point, new AlignmentStepper(), symbol);

            if (winning) {
                String message = String.format("%c's aligned on the %s",
                        symbol, direction.getName()
                );

                // it is GAME OVER
                return new ConclusionDTO().setType(FINISHED)
                        .setMessage(message);
            }
			else if(gameStatus.getTurnCount() == 8) {
				// We ended with no winner
				return new ConclusionDTO().setType(DRAW).setMessage("This is a draw");
			}
        }

        return new ConclusionDTO().setType(ONGOING)
                .setMessage("No player wins");
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

	private void checkIfPlacementValid(GameStatusDTO gameStatus, int turns, int line, int col) {

		if (gameStatus.getConclusion().getType() == FINISHED && turns > 0) {
			throw new IllegalMoveException(String.format(
					"Impossible move. Game over reached. no move should be allowed after the game is won", turns));
		}
		Character symbol;
		if (turns > 8) {
			throw new IllegalMoveException(
					String.format("Impossible move. Game over reached with: %d turns", turns));
			
		}else if ((symbol = gameStatus.getGrid()[line][col] ) != null) {
			// when a not empty cell is targeted
			throw new IllegalMoveException("Impossible move. Position already used. Symbol:" + symbol);
		}
	}
	
    private boolean lookAroundPoint(GameStatusDTO gameStatus, int[] direction, int[] position,
    		AlignmentStepper stepper, Character symbol) {

        // next column c2 and next line l2 to point on a symbol2
        int l2 = position[0] + stepper.step * direction[0];
        int c2 = position[1] + stepper.step * direction[1];

        boolean isNotContainedOrDifferent =
        // (c2 is out of Bounds) || (l2 is out of Bounds) || symbol2 different
        (l2 < 0 || l2 >= totalLines) || (c2 < 0 || c2 >= totalColumns) || (!symbol.equals(gameStatus.getGrid()[l2][c2] ));

        if (isNotContainedOrDifferent) {

            /*
                the research blocked twice in a direction
                => too many times out of bound or different symbols
            */
            if (++stepper.endDirectionFlag == 2) {
                return false;
            }

            /*
                We change the way but keep the same direction.
                So we use a transformed direction matrix : turningBack
            */
            stepper.waySwitch *= -1;
            int turningBackL = direction[0] * stepper.waySwitch;
            int turningBackC = direction[1] * stepper.waySwitch;

            int[] turningBack = new int[]{turningBackL, turningBackC};

            // starting over from the step one. But it will be in the other way
            stepper.step = 1;

            return lookAroundPoint(gameStatus, turningBack, position, stepper, symbol);
        } else {
            ++stepper.step;
            if (goal != ++stepper.score) {
                return lookAroundPoint(gameStatus, direction, position, stepper, symbol);
            }
            return true;
        }
    }
}
