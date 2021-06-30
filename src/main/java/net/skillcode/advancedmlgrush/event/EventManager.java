/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.event;

import com.google.inject.Singleton;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class EventManager {

    private final Map<Class<? extends Event>, List<EventListener<?>>> listeners = new ConcurrentHashMap<>();
    private final Map<EventHandler, List<EventListener<?>>> handlers = new ConcurrentHashMap<>();

    public void registerEventListeners(final @NotNull Iterable<EventListener<?>> eventListeners) {
        eventListeners.forEach(eventListener -> {
            if (listeners.containsKey(eventListener.getEventClass())) {
                listeners.get(eventListener.getEventClass()).add(eventListener);
            } else {
                listeners.put(eventListener.getEventClass(), new CopyOnWriteArrayList<>(Arrays.asList(eventListener)));
            }
        });
    }

    public void registerEventListeners(final @NotNull EventHandler eventHandler) {
        final List<EventListener<?>> eventListeners = new CopyOnWriteArrayList<>();
        handlers.put(eventHandler, eventListeners);
        eventHandler.registerListeners(eventListeners);
        registerEventListeners(eventListeners);
    }

    public void callEvent(final @NotNull Event event) {
        if (listeners.containsKey(event.getClass())) {
            listeners.get(event.getClass()).forEach(eventListener -> eventListener.callEvent(event));
        }
    }

    public void unregister(final @NotNull EventHandler eventHandler) {
        if (handlers.containsKey(eventHandler)) {
            handlers.get(eventHandler).forEach(eventListener -> listeners.values().forEach(eventListeners -> eventListeners.remove(eventListener)));
        }
    }

}
