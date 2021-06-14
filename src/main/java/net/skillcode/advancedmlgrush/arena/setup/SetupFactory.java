package net.skillcode.advancedmlgrush.arena.setup;

import net.skillcode.advancedmlgrush.arena.ArenaType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface SetupFactory {

    TemplateSetup create(final @NotNull Player player, final @NotNull ArenaType arenaType,
                         final @NotNull Runnable onComplete);

}
