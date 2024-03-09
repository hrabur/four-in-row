package pu.fmi.connect4.logic;

import java.util.UUID;

public class GameException extends RuntimeException {

    private final UUID gameId;

    public UUID getGameId() {
        return gameId;
    }

    public GameException(UUID gameId, String message) {
        super(message);
        this.gameId = gameId;
    }
}
