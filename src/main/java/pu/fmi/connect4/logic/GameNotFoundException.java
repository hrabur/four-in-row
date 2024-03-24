package pu.fmi.connect4.logic;

import static java.lang.String.format;

import java.util.UUID;

public class GameNotFoundException extends GameException {

    public GameNotFoundException(UUID gameId) {
        super(gameId, format("Game with ID [%s] does not exist", gameId));
    }
}
