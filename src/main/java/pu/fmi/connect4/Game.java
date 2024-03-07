package pu.fmi.connect4;

import java.util.UUID;


public class Game {

	public static final int COLUMS = 7;
	public static final int ROWS = 6;
	
	private UUID gameId;
	
	private Player[][] board;
	
	private Player turn;

	private Player winner;

	private boolean gameOver;

	public Game() {
		gameId = UUID.randomUUID();
		turn = Player.BLUE;
		board = new Player[ROWS][COLUMS];
	}

	public int findColumnTop(int column) {
		for (int row = 0; row < ROWS; row++) {
			if (board[row][column] == null) {
				return row;
			}
		}

		return -1;
	}

	public boolean isColumnFull(int column) {
		return board[ROWS - 1][column] != null;
	}

	public boolean isBoardFull() {
		for (int col = 0; col < COLUMS; col++) {
			if (isColumnFull(col)) {
				return false;
			}
		}

		return true;
	}

	public UUID getGameId() {
		return gameId;
	}

	public void setGameId(UUID gameId) {
		this.gameId = gameId;
	}

	public Player[][] getBoard() {
		return board;
	}

	public void setBoard(Player[][] board) {
		this.board = board;
	}

	public Player getTurn() {
		return turn;
	}

	public void setTurn(Player turn) {
		this.turn = turn;
	}

	
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	
	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}
}
