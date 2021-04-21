package net.skillcode.advancedmlgrush.placeholder;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface Replaceable {

    String getString(final @NotNull Optional<Player> playerOptional, final @NotNull String input);

}
