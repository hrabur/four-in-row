package pu.fmi.connect4.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pu.fmi.connect4.model.Player.BLUE;
import static pu.fmi.connect4.model.Player.RUBY;

import org.junit.jupiter.api.Test;

import pu.fmi.connect4.model.GameRepoInMemory;
import pu.fmi.connect4.model.Player;

class GameServiceImplTest {

	@Test
	void testStartNewGame() {
		var gameRepo = new GameRepoInMemory();
		var gameService = new GameServiceImpl(gameRepo);
		var game = gameService.startNewGame();

		assertNotNull(game);
		assertNotNull(game.getGameId());
		var storedGame = gameRepo.get(game.getGameId());
		assertEquals(game.getGameId(), storedGame.getGameId());
	}

	@Test
	void testMakeMoveWithCorrectMove() {
		var gameRepo = new GameRepoInMemory();
		var gameService = new GameServiceImpl(gameRepo);
		var game = gameService.startNewGame();

		gameService.makeMove(game.getGameId(), new Move(BLUE, 1));

		var storedGame = gameRepo.get(game.getGameId());
		assertEquals(BLUE, storedGame.getBoard()[0][1]);
		assertEquals(RUBY, game.getTurn());
	}

	@Test
	void testIsPalyerWins() {
		GameServiceImpl gameService = new GameServiceImpl(new GameRepoInMemory());

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
