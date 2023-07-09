package com.example.oxo.model.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePlayersDTO {
	
	private Integer gameId;
    private String[] pseudos;
}
