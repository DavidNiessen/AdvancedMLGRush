package net.skillcode.advancedmlgrush.miscellaneous.registrable;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Registrable {

    void register(final @NotNull Player player);

    void unregister(final @NotNull Player player);

}
