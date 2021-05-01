package net.skillcode.advancedmlgrush.event;

import com.google.inject.Singleton;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class EventManager {

    private final Map<Class<? extends Event>, List<EventListener<?>>> listeners = new ConcurrentHashMap<>();

    public void registerEventListeners(final @NotNull Iterable<EventListener<?>> eventListeners) {
        eventListeners.forEach(eventListener -> {
            if (listeners.containsKey(eventListener.getEventClass())) {
                listeners.get(eventListener.getEventClass()).add(eventListener);
            } else {
                listeners.put(eventListener.getEventClass(), new ArrayList<>(Arrays.asList(eventListener)));
            }
        });
    }

    public void registerEventListeners(final @NotNull EventHandler eventHandler) {
        final List<EventListener<?>> eventListeners = new ArrayList<>();
        eventHandler.registerListeners(eventListeners);

        registerEventListeners(eventListeners);
    }

    public void callEvent(final @NotNull Event event) {
        if (listeners.containsKey(event.getClass())) {
            listeners.get(event.getClass()).forEach(eventListener -> eventListener.callEvent(event));
        }
    }

}
