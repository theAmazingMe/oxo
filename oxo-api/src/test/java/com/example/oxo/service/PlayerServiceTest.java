package com.example.oxo.service;

import com.example.oxo.model.DTO.CreatePlayersDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PlayerServiceTest {
    @Autowired
    PlayerService playerService;

    @Test
    void itCreatesPlayersSuccessfully(){
        Assertions.assertNotNull(playerService.createPlayers(createPlayersCreated()));
    }

    @ParameterizedTest ( name = "Fails when : {1}")
    @MethodSource
    void itFailsCreatingPlayers(String[] invalidPseudoList,String ignoreDescriptions){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> playerService.createPlayers(CreatePlayersDTO.builder()
                .pseudos(invalidPseudoList)
                .build()
        ));

    }

    private static Stream<Arguments> itFailsCreatingPlayers(){
        return Stream.of(
                Arguments.of(new String[] {"John","Doe","Bob","Alice"},"Too many players"),
                Arguments.of(new String[] {"Doe","Doe"},"Same player"),
                Arguments.of(new String[] {"Only John"},"Single player"),
                Arguments.of(null,"Null list"),
                Arguments.of(new String[] {}, "No player")
        );
    }

    private CreatePlayersDTO createPlayersCreated() {
        return CreatePlayersDTO.builder()
                .pseudos(new String[]{"John","Doe"})
                .build();
    }
}