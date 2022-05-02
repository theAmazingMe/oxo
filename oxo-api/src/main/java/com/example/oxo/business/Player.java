package com.example.oxo.business;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
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
