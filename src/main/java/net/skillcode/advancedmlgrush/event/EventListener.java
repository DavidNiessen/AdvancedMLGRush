/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin from SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public abstract class EventListener<E extends Event> {

    private final Class<? extends Event> eventClass;

    protected abstract void onEvent(final @NotNull E event);

    public void callEvent(final @NotNull Event event) {
        if (event.getClass().equals(eventClass)) {
            onEvent((E) event);
        }
    }


}
