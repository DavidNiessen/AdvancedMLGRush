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

package com.skillplugins.advancedmlgrush.protocol;

import com.google.inject.Injector;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.Initializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProtocolInitializer implements Initializer {

    private final JavaPlugin javaPlugin;

    @Inject
    public ProtocolInitializer(final JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            injector.getInstance(BlockBreakSpeedAdjuster.class);
        } else {
            javaPlugin.getLogger().warning(Constants.PROTOCOLLIB_NOT_FOUND_MESSAGE);
        }
    }
}
