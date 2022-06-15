package com.example.oxo.service.impl;

import java.util.Arrays;
import java.util.List;

import com.example.oxo.model.GameStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.oxo.business.Player;
import com.example.oxo.model.CreatePlayersDTO;
import com.example.oxo.service.PlayerService;
import com.example.oxo.service.TicTacToeService;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private TicTacToeService ticTacToe;

    @Override
    public GameStatusDTO createPlayers(CreatePlayersDTO dto){
    	if(dto.getPseudos() == null) {
        	throw new IllegalArgumentException("Invalid players list");
    	}
        List<Player> players = Arrays.asList(dto.getPseudos()).stream()
                .filter(pseudo -> pseudo.length()>1)
                .map(pseudo -> new Player().setPseudo(pseudo))
        		.toList();
        
        return ticTacToe.initializePlayers(dto.getGameId(), players);
    }
}
