package pu.fmi.connect4;

import java.util.UUID;

public interface GameService {

	Game startNewGame();
	
	void makeMove(Move move);
	
	Game getGame(UUID gameId);
}
