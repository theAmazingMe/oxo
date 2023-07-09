package com.example.oxo.service.impl;

import com.example.oxo.business.Player;
import com.example.oxo.model.DTO.CreatePlayersDTO;
import com.example.oxo.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Override
    public List<Player> createPlayers(CreatePlayersDTO dto){

        checkPlayersToCreate(dto.getPseudos());

        return createPlayersFromPseudos(dto);
    }

    public void checkPlayersToCreate(String[] pseudos) {
        if(pseudos == null ) {
            throw new IllegalArgumentException("Invalid players list");
        }
        if(pseudos.length != 2){
            throw new IllegalArgumentException("Invalid players list, players should be two");
        }
        if(pseudos[0].trim().equals(pseudos[1].trim())){
            throw new IllegalArgumentException("Invalid players list, players should not be the same");
        }
    }

    @Override
    public void checkPlayersToCreate(List<Player> players) {
        this.checkPlayersToCreate(getPseudos(players));
    }

    @Override
    public String[] getPseudos(List<Player> players) {
        if(players == null)
            return new String[]{};

        return players.stream()
                .map(Player::getPseudo)
                .toList()
                .toArray(new String[0]);
    }

    private List<Player> createPlayersFromPseudos(CreatePlayersDTO dto) {
        return Arrays.asList(dto.getPseudos()).stream()
                .filter(pseudo -> pseudo.length()>1)
                .map(pseudo -> Player.builder().pseudo(pseudo).build())
                .toList();
    }
}
