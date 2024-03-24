package pu.fmi.connect4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static pu.fmi.connect4.model.Player.BLUE;
import static pu.fmi.connect4.model.Player.RUBY;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import pu.fmi.connect4.logic.GameService;
import pu.fmi.connect4.logic.PlayerMove;
import pu.fmi.connect4.model.Game;
import pu.fmi.connect4.model.Player;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class Connect4ApplicationTests {

	@Autowired
	private GameService gameService;

	@Test
	void testGetGameById(@Autowired WebTestClient client) {
		Game expectedGame = gameService.startNewGame();
		gameService.makeMove(expectedGame.getGameId(), new PlayerMove(Player.BLUE, 0));
		expectedGame = gameService.getGame(expectedGame.getGameId());

		Game actualGame = client
			.get()
			.uri("/games/{gameId}", expectedGame.getGameId())
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Game.class)
			.returnResult()
			.getResponseBody();

		assertThat(actualGame.getGameId()).isEqualTo(expectedGame.getGameId());
		assertThat(actualGame.getTurn()).isEqualTo(expectedGame.getTurn());
		assertThat(actualGame.getMoves().size()).isEqualTo(expectedGame.getMoves().size());
	}

	@Test
	void testStartNewGame(@Autowired WebTestClient client) {
		var timeBeforeGameStart = LocalDateTime.now();
		client
			.post()
			.uri("/games")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Game.class)
			.value(game -> {
				assertThat(game.getGameId()).isNotNull();
				assertThat(game.getTurn()).isEqualTo(Player.BLUE);
				assertThat(game.getMoves()).isEmpty();
				assertThat(game.getStartTime())
					.isAfter(timeBeforeGameStart)
					.isBefore(LocalDateTime.now());
				assertThat(game.getEndTime()).isNull();
			});
	}

	@Test
	void testListGames(@Autowired WebTestClient client) {
		Game oldestGame = gameService.startNewGame();
		for (int i = 0; i < 10; i++) {
			gameService.startNewGame();
		}

		client
			.get()
			.uri("/games")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(Game.class)
			.hasSize(10)
			.value(games -> {
				assertThat(games).noneMatch(game -> oldestGame.getGameId().equals(game.getGameId()));
			});
	}

	@Test
	void testMakeMove(@Autowired WebTestClient client) {
		Game game = gameService.startNewGame();

		var timeBeforeMove = LocalDateTime.now();
		client
			.post()
			.uri("/games/{gameId}/moves", game.getGameId())
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(new PlayerMove(Player.BLUE, 1))
			.exchange()
			.expectStatus()
			.isOk();

		Game gameAferMove = gameService.getGame(game.getGameId());
		assertThat(gameAferMove.getMoves()).hasSize(1);
		assertThat(gameAferMove.getMoves().get(0).getTime())
			.isAfter(timeBeforeMove)
			.isBefore(LocalDateTime.now());
		assertThat(gameAferMove.getTurn()).isEqualTo(Player.RUBY);
	}

	@Test
	void testMakeMoveAndWin(@Autowired WebTestClient client) {
		Game game = gameService.startNewGame();
		Stream.of(
			new PlayerMove(BLUE,2),
			new PlayerMove(RUBY,3),
			new PlayerMove(BLUE,2),
			new PlayerMove(RUBY,4),
			new PlayerMove(BLUE,3),
			new PlayerMove(RUBY,2),
			new PlayerMove(BLUE,3),
			new PlayerMove(RUBY,4),
			new PlayerMove(BLUE,3),
			new PlayerMove(RUBY,4)
		).forEach(move -> {
			gameService.makeMove(game.getGameId(), move);
		});

		LocalDateTime timeBeforeMove = LocalDateTime.now();
		client
			.post()
			.uri("/games/{gameId}/moves", game.getGameId())
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(new PlayerMove(BLUE, 3))
			.exchange()
			.expectStatus()
			.isOk();

		Game gameAferMove = gameService.getGame(game.getGameId());
		assertThat(gameAferMove.getMoves()).hasSize(11);
		assertThat(gameAferMove.isGameOver()).isTrue();
		assertThat(gameAferMove.getWinner()).isEqualTo(BLUE);
		assertThat(gameAferMove.getEndTime())
			.isAfter(timeBeforeMove)
			.isBefore(LocalDateTime.now());
	}

}
