package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.event.EventManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class AsyncPlayerChatListener implements Listener {

    @Inject
    private EventManager eventManager;

    @EventHandler
    public void onInteract(final @NotNull AsyncPlayerChatEvent event) {
        eventManager.callEvent(event);
    }

}
