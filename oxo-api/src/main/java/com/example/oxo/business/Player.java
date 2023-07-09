package com.example.oxo.business;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
public class Player{

    private String pseudo;
    private int wins;
    private int loss;

    public void defeat() {
        this.loss++;
    }

    public void victory() {
        this.wins++;
    }
}
