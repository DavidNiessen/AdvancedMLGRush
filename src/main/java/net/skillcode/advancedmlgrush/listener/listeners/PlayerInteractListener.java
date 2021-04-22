package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PlayerInteractListener implements Listener {

    @Inject
    private EventManager eventManager;

    @EventHandler
    public void onInteract(final @NotNull PlayerInteractEvent event) {
        eventManager.callEvent(event);
    }

}
