package pu.fmi.connect4.logic;

import java.util.UUID;

import pu.fmi.connect4.model.Game;

public interface GameService {

	Game startNewGame();
	
	void makeMove(UUID gameId, Move move);
	
	Game getGame(UUID gameId);

}
