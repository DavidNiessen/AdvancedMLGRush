package net.skillcode.advancedmlgrush.arena.setup;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface SetupStep {

    void run(final @NotNull Player player);

}
