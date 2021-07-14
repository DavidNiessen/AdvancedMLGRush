/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: http://discord.skillplugins.com
 */

package com.skillplugins.advancedmlgrush.miscellaneous.registrable;

import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class RegistrableManager {

    private final List<Registrable> registrables = new CopyOnWriteArrayList<>();

    public void registerRegistrable(final @NotNull Registrable registrable) {
        if (!registrables.contains(registrable)) {
            registrables.add(registrable);
        }
    }

    public void unregisterPlayer(final @NotNull Player player) {
        registrables.forEach(registrable -> registrable.unregister(player));
    }

}
