package pu.fmi.connect4.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class Game {

	public static final int COLUMNS = 7;
	public static final int LEVELS = 6;
	
	@Id
	private UUID gameId;
	
	@OrderBy("time ASC")
	@JoinColumn(name = "gameId")
	@OneToMany(cascade = ALL, fetch = EAGER)
	private List<Move> moves;

	private Player turn;
	private Player winner;
	private boolean gameOver;

	// TODO: Add start time property
	private LocalDateTime endTime;

	public Game() {
		gameId = UUID.randomUUID();
		turn = Player.BLUE;
		moves = new ArrayList<>();
	}

	public UUID getGameId() {
		return gameId;
	}

	public void setGameId(UUID gameId) {
		this.gameId = gameId;
	}
	
	public List<Move> getMoves() {
		return moves;
	}

	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}

	public Player[][] getBoard() {
		Player[][] board = new Player[LEVELS][COLUMNS];
		for (Move move : moves) {
			board[move.getLevel()][move.getColumn()] = move.getPlayer();
		}
		
		return board;
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

	public void markGameOver() {
		setGameOver(true);
		setEndTime(LocalDateTime.now());
	}
	
	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
}
