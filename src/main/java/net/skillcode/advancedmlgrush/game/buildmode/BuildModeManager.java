/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.game.buildmode;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class BuildModeManager implements Registrable {

    private final Map<Player, Boolean> buildModeMap = new ConcurrentHashMap<>();

    public void register(final @NotNull Player player) {
        buildModeMap.put(player, false);
    }

    public boolean isRegistered(final @NotNull Player player) {
        return buildModeMap.containsKey(player);
    }

    public void setBuildMode(final @NotNull Player player, final boolean buildMode) {
        buildModeMap.put(player, buildMode);
    }

    public boolean isInBuildMode(final @NotNull Player player) {
        return buildModeMap.getOrDefault(player, false);
    }

    @Override
    public void unregister(final @NotNull Player player) {
        buildModeMap.remove(player);
    }
}
