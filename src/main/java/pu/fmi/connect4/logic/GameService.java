package pu.fmi.connect4.logic;

import java.util.Collection;
import java.util.UUID;

import pu.fmi.connect4.model.Game;

public interface GameService {

	Game startNewGame();
	
	void makeMove(UUID gameId, PlayerMove move);
	
	Game getGame(UUID gameId);

	Collection<Game> listGames();

}
