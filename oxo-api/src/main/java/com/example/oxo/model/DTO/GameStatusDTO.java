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

    public GameStatusDTO(){};

    public GameStatusDTO(GameStatusDTOBuilder builder){
        this.gameId = builder.gameId;
        this.conclusion = builder.conclusion;
        this.grid = builder.grid;
        this.players = builder.players;
        this.turnCount = builder.turnCount;
    }

    public static GameStatusDTOBuilder builder(){
        return new GameStatusDTOBuilder();
    }

    public static class GameStatusDTOBuilder{
        private Integer gameId;
        private ConclusionDTO conclusion;
        private Character[][] grid;
        private List<Player> players;
        private int turnCount;

        public GameStatusDTOBuilder gameId(Integer gameId){
            this.gameId = gameId;
            return this;
        }

        public GameStatusDTOBuilder conclusion(ConclusionDTO.ConclusionDTOBuilder builder){
            this.conclusion = builder.build();
            return this;
        }

        public GameStatusDTOBuilder conclusion(ConclusionDTO conclusion){
            this.conclusion = conclusion;
            return this;
        }

        public GameStatusDTOBuilder grid(Character[][] grid){
            this.grid = grid;
            return this;
        }

        public GameStatusDTOBuilder players(List<Player> players){
            this.players = players;
            return this;
        }

        public GameStatusDTOBuilder turnCount(Integer turnCount){
            this.turnCount = turnCount;
            return this;
        }

        public GameStatusDTO build(){
            return new GameStatusDTO(this);
        }
    }
}
