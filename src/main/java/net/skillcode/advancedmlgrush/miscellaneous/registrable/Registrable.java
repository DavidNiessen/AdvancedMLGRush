package net.skillcode.advancedmlgrush.miscellaneous.registrable;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Registrable {

    void unregister(final @NotNull Player player);

}
