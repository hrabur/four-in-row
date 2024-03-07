package pu.fmi.connect4;

import static java.lang.String.format;

import java.util.UUID;

public class GameNotFoundException extends RuntimeException {
    private final UUID gameId;

    public GameNotFoundException(UUID gameId) {
        super(format("Game with ID [%s] does not exist", gameId));
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }
}
