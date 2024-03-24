package pu.fmi.connect4.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Move {
	
	public Move() {
		time = LocalDateTime.now();
	}

	public Move(int column, int level, Player player) {
		this();
		this.column = column;
		this.level = level;
		this.player = player;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID moveId;

	private int column;
	private int level;
	private Player player;
	private LocalDateTime time;

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public UUID getMoveId() {
		return moveId;
	}

	public void setMoveId(UUID moveId) {
		this.moveId = moveId;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int col) {
		this.column = col;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
