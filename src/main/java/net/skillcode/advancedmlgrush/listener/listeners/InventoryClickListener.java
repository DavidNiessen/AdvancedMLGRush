package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class InventoryClickListener implements Listener {

    @Inject
    private EventManager eventManager;

    @EventHandler
    public void onClick(final @NotNull InventoryClickEvent event) {
        eventManager.callEvent(event);
    }

}
