package com.example.oxo.service;

import com.example.oxo.business.Player;
import com.example.oxo.model.DTO.CreatePlayersDTO;

import java.util.List;

public interface PlayerService {

	/**
	 * Register players based on pseudos
	 *
	 * @param dto
	 * @return
	 */
	List<Player> createPlayers(CreatePlayersDTO dto);
	
	void checkPlayersToCreate(String[] pseudos);

	void checkPlayersToCreate(List<Player> players);

	String[] getPseudos(List<Player> players);
}
