package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.inventory.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class InventoryCloseListener implements Listener {

    @Inject
    private EventManager eventManager;
    @Inject
    private InventoryManager inventoryManager;

    @EventHandler
    public void onClose(final @NotNull InventoryCloseEvent event) {
        eventManager.callEvent(event);

        inventoryManager.unregister((Player) event.getPlayer());
    }

}
