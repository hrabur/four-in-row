package pu.fmi.connect4;

import java.util.UUID;

public record Move(UUID gameId, Player player, int column) {
}
