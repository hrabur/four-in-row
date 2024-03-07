package pu.fmi.connect4;

import java.util.UUID;

public interface GameRepo {

	void save(Game game);

	Game get(UUID gameId);

	void delete(Game game);

}