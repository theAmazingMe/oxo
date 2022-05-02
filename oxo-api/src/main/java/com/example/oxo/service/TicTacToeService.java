package com.example.oxo.service;

import java.util.List;

import com.example.oxo.business.Player;
import com.example.oxo.model.GameStatusDTO;

public interface TicTacToeService {

	GameStatusDTO startNewGame();

	GameStatusDTO startNewGame(Integer id);

	GameStatusDTO initializePlayers(Integer gameId, List<Player> players);

	void turnCountUp(Integer id);

	GameStatusDTO resetPlayersScores(Integer id);

	GameStatusDTO getStatus(Integer id);

	GameStatusDTO switchTurns(Integer id);
}
