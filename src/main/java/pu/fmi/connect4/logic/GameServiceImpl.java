package pu.fmi.connect4.logic;

import static java.lang.String.format;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pu.fmi.connect4.model.Game;
import pu.fmi.connect4.model.GameRepo;
import pu.fmi.connect4.model.Player;

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
	public void makeMove(UUID gameId, Move move) {
		var game = getGame(gameId);

		validateGameIsNotOver(game, move);
		validateTurn(game, move);
		validateColumnIsNotFull(game, move);

		int colTop = findColumnTop(game, move.column());
		game.getBoard()[colTop][move.column()] = move.player();
		// TODO: Make switch which player is on turn
		
		if (checkIfPlayerWins(move.player(), game.getBoard())) {
			game.setWinner(move.player());
			game.setGameOver(true);
			return;
		}

		if (isBoardFull(game)) {
			game.setGameOver(true);
		}
	}

	private void validateGameIsNotOver(Game game, Move move) {
		if (game.isGameOver()) {
			throw new IllegalMoveException(game.getGameId(),
				format("Move of player [%s] is not possible. Game [%s] is over",
				move.player(), game.getGameId()));
		}
	}

	private void validateColumnIsNotFull(Game game, Move move) {
		if (isColumnFull(game, move.column())) {
			throw new IllegalMoveException(game.getGameId(),
				format("Move of player [%s] is not possible. Column [%d] of game [%s] is full",
				move.player(), move.column(), game.getGameId()));
		}
	}

	private void validateTurn(Game game, Move move) {
		if (!game.getTurn().equals(move.player())) {
			throw new IllegalMoveException(game.getGameId(),
				format(
					"Move of player [%s] is not possible. In game [%s] it is player [%s] turn",
					move.player(), game.getGameId(), game.getTurn()));
		}
	}

	int findColumnTop(Game game, int column) {
		var board = game.getBoard();
		for (int row = 0; row < Game.ROWS; row++) {
			if (board[row][column] == null) {
				return row;
			}
		}

		return -1;
	}

	public boolean isColumnFull(Game game, int column) {
		var board = game.getBoard();
		return board[Game.ROWS - 1][column] != null;
	}

	public boolean isBoardFull(Game game) {
		for (int col = 0; col < Game.COLUMS; col++) {
			if (isColumnFull(game, col)) {
				return false;
			}
		}

		return true;
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
