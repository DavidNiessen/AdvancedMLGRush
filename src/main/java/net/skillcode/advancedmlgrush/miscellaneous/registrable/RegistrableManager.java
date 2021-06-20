/*
 * Copyright (c) 2021 SkillCode
 *
 * This class is a part of the source code of the
 * AdvancedMLGRush plugin from SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.miscellaneous.registrable;

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
