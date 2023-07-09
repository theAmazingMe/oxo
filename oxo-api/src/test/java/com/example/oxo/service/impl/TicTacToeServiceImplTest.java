package com.example.oxo.service.impl;

import com.example.oxo.business.Player;
import com.example.oxo.exception.ResourceNotFoundException;
import com.example.oxo.model.DTO.GameStatusDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class TicTacToeServiceImplTest {

    @InjectMocks
    private TicTacToeServiceImpl ticTacToeService;

    @Mock
    private Map<Integer, GameStatusDTO> storedGames;

    @Mock
    private PlayerServiceImpl playerService;
    private static final Integer FIRST_GAME = 1;

    @Test
    void itCreatesANewGameWithDefault(){
        Assertions.assertNotNull(ticTacToeService.startNewGame());
    }

    @Test
    void itCreatesANewGameWithId(){

        stubGame();
        Assertions.assertNotNull(ticTacToeService.startNewGame(FIRST_GAME));
    }

    private void stubGame() {
        when(storedGames.get(FIRST_GAME)).thenReturn(GameStatusDTO.builder()
                .players(getTwoPlayers())
                .build()
        );
    }

    @Test
    void itSwapPlayersWhenSwitchingTurns(){
        stubGame();
        String initialPseudo = storedGames.get(FIRST_GAME).getPlayers().get(0).getPseudo();
        GameStatusDTO modifiedGame = ticTacToeService.switchTurns(FIRST_GAME);
        String swappedPseudo = modifiedGame.getPlayers().get(0).getPseudo();

        Assertions.assertNotEquals(initialPseudo,swappedPseudo);
    }

    @Test
    void itGetsStatusForExistingId(){
        Assertions.assertDoesNotThrow(() -> {
            ticTacToeService.init();
            ticTacToeService.startNewGame();
            ticTacToeService.getStatus(FIRST_GAME);
        });
    }

    @Test
    void itFailsRetrievingAGameIfNoneIsStarted(){
        Assertions.assertThrows(ResourceNotFoundException.class,() -> {
            ticTacToeService.init();
            ticTacToeService.getStatus(FIRST_GAME);
        });
    }

    List<Player> getTwoPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(Player.builder().pseudo("John").build());
        players.add(Player.builder().pseudo("Doe").build());
        return players;
    }
}