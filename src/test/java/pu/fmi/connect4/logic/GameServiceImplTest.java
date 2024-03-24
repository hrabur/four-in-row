package pu.fmi.connect4.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pu.fmi.connect4.model.Player.BLUE;
import static pu.fmi.connect4.model.Player.RUBY;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pu.fmi.connect4.model.GameRepository;
import pu.fmi.connect4.model.Player;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameRepository gameRepo;

	@Test
	void testStartNewGame() {
		var gameService = new GameServiceImpl(gameRepo);
		var game = gameService.startNewGame();

		assertNotNull(game);
		assertNotNull(game.getGameId());
        assertNotNull(game.getTurn());
        assertEquals(0, game.getMoves().size());
        verify(gameRepo).save(game);
	}

	@Test
	void testMakeMoveWithCorrectMove() {
		var gameService = new GameServiceImpl(gameRepo);
		var game = gameService.startNewGame();
        when(gameRepo.findById(any(UUID.class))).thenReturn(Optional.of(game));

		gameService.makeMove(game.getGameId(), new PlayerMove(BLUE, 1));

		assertEquals(BLUE, game.getBoard()[0][1]);
		assertEquals(RUBY, game.getTurn());
	}

	@Test
	void testIsPalyerWins() {
		GameServiceImpl gameService = new GameServiceImpl(gameRepo);

		Player[][] noWinner = {
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, null, null, BLUE, null, null, null },
			{ null, null, RUBY, BLUE, null, null, null },
			{ null, null, BLUE, BLUE, RUBY, null, null },
			{ null, null, BLUE, RUBY, RUBY, RUBY, BLUE }
		};
		assertFalse(gameService.checkIfPlayerWins(BLUE, noWinner));

		Player[][] up = {
			{ null, null, null, null, null, null, null },
			{ null, null, null, BLUE, null, null, null },
			{ null, null, null, BLUE, null, null, null },
			{ null, null, RUBY, BLUE, null, null, null },
			{ null, null, BLUE, BLUE, RUBY, null, null },
			{ null, null, BLUE, RUBY, RUBY, RUBY, null }
		};
		assertTrue(gameService.checkIfPlayerWins(BLUE, up));

		Player[][] right = {
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, RUBY, null, null, null, null, null },
			{ null, RUBY, BLUE, null, null, null, null },
			{ null, RUBY, RUBY, null, null, null, null },
			{ null, BLUE, BLUE, BLUE, BLUE, null, null }
		};
		assertTrue(gameService.checkIfPlayerWins(BLUE, right));

		Player[][] rightUp = {
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, RUBY, null },
				{ null, null, null, null, RUBY, RUBY, null },
				{ null, null, RUBY, RUBY, BLUE, BLUE, BLUE },
				{ null, BLUE, RUBY, BLUE, BLUE, BLUE, RUBY }
		};
		assertTrue(gameService.checkIfPlayerWins(RUBY, rightUp));

		Player[][] rightDown = {
			{ null, null, null, null, null, null, null },
			{ null, null, null, null, null, null, null },
			{ null, RUBY, null, null, null, null, null },
			{ null, RUBY, RUBY, null, null, null, null },
			{ null, BLUE, BLUE, RUBY, null, null, BLUE },
			{ null, BLUE, RUBY, BLUE, RUBY, null, BLUE }
		};
		assertTrue(gameService.checkIfPlayerWins(RUBY, rightDown));
	}

}
