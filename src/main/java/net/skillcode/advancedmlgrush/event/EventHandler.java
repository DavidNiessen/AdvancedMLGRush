package net.skillcode.advancedmlgrush.event;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface EventHandler {

    void registerListeners(final @NotNull List<EventListener<?>> eventListeners);

}
