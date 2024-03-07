package pu.fmi.connect4;

import static java.lang.String.format;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GameServiceImpl implements GameService {

	private Logger log = LoggerFactory.getLogger(GameServiceImpl.class);
	
	private GameRepo gameRepo;

	/**
	 * Demo of constructor injection. The rocomended way for all required dependencies.
	 * For more details see {@link https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html#beans-constructor-injection}
	 * 
	 * @param gameRepo
	 */
	public GameServiceImpl(GameRepo gameRepo) {
		log.info("GameServiceImpl constructor");
		this.gameRepo = gameRepo;
	}
	
	@Override
	public Game startNewGame() {
		var game = new Game();
		gameRepo.save(game);
		return game;
	}

	@Override
	public void makeMove(Move move) {
		var game = getGame(move.gameId());

		validateGameIsNotOver(move, game);
		validateTurn(move, game);
		validateColumnIsNotFull(move, game);

		int colTop = game.findColumnTop(move.column());
		game.getBoard()[colTop + 1][move.column()] = move.player();

		if (checkIfPlayerWins(move.player(), game.getBoard())) {
			game.setWinner(move.player());
			game.setGameOver(true);
			return;
		}

		if (game.isBoardFull()) {
			game.setGameOver(true);
		}
	}

	private void validateGameIsNotOver(Move move, Game game) {
		if (game.isGameOver()) {
			throw new IllegalMoveException(move.gameId(),
				format("Move of player [%s] is not possible. Game [%s] is over",
				move.player(), game.getGameId()));
		}
	}

	private void validateColumnIsNotFull(Move move, Game game) {
		if (game.isColumnFull(move.column())) {
			throw new IllegalMoveException(move.gameId(),
				format("Move of player [%s] is not possible. Column [%d] of game [%s] is full",
				move.player(), move.column(), game.getGameId()));
		}
	}

	private void validateTurn(Move move, Game game) {
		if (!game.getTurn().equals(move.player())) {
			throw new IllegalMoveException(move.gameId(),
				format(
					"Move of player [%s] is not possible. In game [%s] it is player [%s] turn",
					move.player(), game.getGameId(), game.getTurn()));
		}
	}

	boolean checkIfPlayerWins(Player player, Player[][] board) {
		// scan from botom/left up and right
		for (int row = 0; row < Game.ROWS; row++) {
			for (int col = 0; col < Game.COLUMS; col++) {
				if (board[row][col] != player) {
					continue;
				}

				if (col + 3 < Game.ROWS &&
					player == board[row][col + 1] && // look right
					player == board[row][col + 2] &&
					player == board[row][col + 3])
					return true;
				
				if (row + 3 < Game.COLUMS) {
					if (player == board[row + 1][col] && // look up
						player == board[row + 2][col] &&
						player == board[row + 3][col])
						return true;

					if (col + 3 < Game.ROWS &&
						player == board[row + 1][col + 1] && // look up & right
						player == board[row + 2][col + 2] &&
						player == board[row + 3][col + 3])
						return true;

					if (col - 4 > 0 &&
						player == board[row + 1][col - 1] && // look down & right
						player == board[row + 2][col - 2] &&
						player == board[row + 3][col - 3])
						return true;
				}
			}
		}

		return false; // no winner found
	}

	@Override
	public Game getGame(UUID gameId) {
		var game = gameRepo.get(gameId);
		if (game == null) {
			throw new GameNotFoundException(gameId);
		}

		return game;
	}

}
