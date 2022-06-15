package com.example.oxo.service;

import com.example.oxo.model.DTO.CreatePlayersDTO;
import com.example.oxo.model.DTO.GameStatusDTO;

public interface PlayerService {

	/**
	 * Register players based on pseudos
	 * @param dto
	 * @return
	 */
	GameStatusDTO createPlayers(CreatePlayersDTO dto);
}
