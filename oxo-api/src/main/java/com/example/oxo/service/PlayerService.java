package com.example.oxo.service;

import com.example.oxo.model.CreatePlayersDTO;
import com.example.oxo.model.GameStatusDTO;

public interface PlayerService {

	/**
	 * Register players based on pseudos
	 * @param dto
	 * @return
	 */
	GameStatusDTO createPlayers(CreatePlayersDTO dto);
}
