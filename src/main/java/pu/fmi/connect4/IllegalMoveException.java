package pu.fmi.connect4;

import java.util.UUID;

public class IllegalMoveException extends GameException {

    public IllegalMoveException(UUID gameId, String message) {
        super(gameId, message);
    }
}
