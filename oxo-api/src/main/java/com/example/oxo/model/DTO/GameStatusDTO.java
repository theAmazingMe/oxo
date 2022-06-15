package com.example.oxo.model.DTO;

import com.example.oxo.business.Player;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GameStatusDTO {
	
	private Integer gameId;
    private ConclusionDTO conclusion;
    private Character[][] grid;
    private List<Player> players;
    private int turnCount;
}
