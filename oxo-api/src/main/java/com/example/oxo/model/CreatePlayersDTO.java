package com.example.oxo.model;

import lombok.Data;

@Data
public class CreatePlayersDTO {
	
	private Integer gameId;
    private String[] pseudos;
}
