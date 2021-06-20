package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.game.buildmode.BuildModeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class InventoryClickListener implements Listener {

    @Inject
    private EventManager eventManager;
    @Inject
    private BuildModeManager buildModeManager;

    @EventHandler
    public void onClick(final @NotNull InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            final Player player = (Player) event.getWhoClicked();
            if (!buildModeManager.isInBuildMode(player)) {
                event.setCancelled(true);
            }

            eventManager.callEvent(event);
        }

    }

}
