package pu.fmi.connect4.api;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pu.fmi.connect4.logic.GameService;
import pu.fmi.connect4.logic.PlayerMove;
import pu.fmi.connect4.model.Game;

@RestController
@RequestMapping("/games")
public class GameController {
	
	/**
	 * Demo of filed injection (not recomended)
	 */
	@Autowired
	public GameService gameService;

	@PostMapping
	public Game startNewGame() {
		return gameService.startNewGame();
	}
	
	@GetMapping("/{gameId}")
	public Game getGame(@PathVariable UUID gameId) {
		return gameService.getGame(gameId);
	}
	
	@PostMapping("/{gameId}/moves")
	public void makeMove(
			@PathVariable UUID gameId,
			@RequestBody @Valid PlayerMove move) {
		gameService.makeMove(gameId, move);
	}
	
	@GetMapping
	public Collection<Game> listGames() {
		return gameService.listGames();
	}
}
