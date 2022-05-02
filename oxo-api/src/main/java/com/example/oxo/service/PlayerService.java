package com.example.oxo.service;

import com.example.oxo.model.CreatePlayersDTO;
import com.example.oxo.model.GameStatusDTO;

public interface PlayerService {
	
	GameStatusDTO createPlayers(CreatePlayersDTO dto);
	
}
