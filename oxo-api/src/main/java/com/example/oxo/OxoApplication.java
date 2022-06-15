package com.example.oxo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.oxo.service.TicTacToeService;

@SpringBootApplication
public class OxoApplication {

//	public TicTacToe game = new TicTacToe();

	public static void main(String[] args) {
		SpringApplication.run(OxoApplication.class, args);
	}

}
