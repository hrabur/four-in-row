package pu.fmi.connect4.logic;

import jakarta.validation.constraints.NotNull;
import pu.fmi.connect4.model.Player;

public record PlayerMove(@NotNull Player player, @NotNull Integer column) {
}
