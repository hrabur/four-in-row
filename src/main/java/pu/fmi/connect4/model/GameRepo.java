package pu.fmi.connect4.model;

import java.util.Collection;
import java.util.UUID;

public interface GameRepo {

	void save(Game game);

	Game get(UUID gameId);

	void delete(Game game);

    Collection<Game> listAll();

}