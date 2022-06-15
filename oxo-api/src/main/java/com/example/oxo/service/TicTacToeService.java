package com.example.oxo.service;

import java.util.List;

import com.example.oxo.business.Player;
import com.example.oxo.model.DTO.GameStatusDTO;

public interface TicTacToeService {

	/**
	 * Create a new game. An ID will be generated for the newest game.
	 * This ID will be incremented based on the last one
	 * @return
	 */
	GameStatusDTO startNewGame();

	/**
	 * Create a new game. No new ID will be generated.
	 * The id given will be used to fetch an existing game
	 * @param gameId
	 * @return
	 */
	GameStatusDTO startNewGame(Integer gameId);

	/**
	 * Register players based on a list of players, for an existing game identified by gameId
	 * @param gameId
	 * @param players
	 * @return
	 */
	GameStatusDTO initializePlayers(Integer gameId, List<Player> players);

	/**
	 * This can set all scores for contestant players to zero.
	 * @param gameId
	 * @return
	 */
	GameStatusDTO resetPlayersScores(Integer gameId);

	/**
	 * Gives the actual state of an existing game.
	 * The game can be either being plaid or still in setting up, it will be load as it is.
	 * @param gameId
	 * @return
	 */
	GameStatusDTO getStatus(Integer gameId);

	/**
	 * Permit to switch symbols assigned to players.
	 * X always starting first, this can let to more flexibility.
	 * At the beginning of a game, we can then decide who plays first.
	 * @param gameId
	 * @return
	 */
	GameStatusDTO switchTurns(Integer gameId);
}
