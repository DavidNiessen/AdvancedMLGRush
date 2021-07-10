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

package net.skillcode.advancedmlgrush.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.command.commands.*;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Singleton
public class CommandInitializer implements Initializer {

    private final JavaPlugin javaPlugin;

    @Inject
    public CommandInitializer(final @NotNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        javaPlugin.getCommand("build").setExecutor(injector.getInstance(BuildCommand.class));
        javaPlugin.getCommand("setspawn").setExecutor(injector.getInstance(SetSpawnCommand.class));
        javaPlugin.getCommand("setupmap").setExecutor(injector.getInstance(SetupMapCommand.class));
        javaPlugin.getCommand("quit").setExecutor(injector.getInstance(QuitCommand.class));
        javaPlugin.getCommand("queue").setExecutor(injector.getInstance(QueueCommand.class));
        javaPlugin.getCommand("settings").setExecutor(injector.getInstance(SettingsCommand.class));
        javaPlugin.getCommand("spectate").setExecutor(injector.getInstance(SpectateCommand.class));
        javaPlugin.getCommand("gadgets").setExecutor(injector.getInstance(GadgetsCommand.class));
        javaPlugin.getCommand("stats").setExecutor(injector.getInstance(StatsCommand.class));
        javaPlugin.getCommand("statsreset").setExecutor(injector.getInstance(StatsResetCommand.class));
    }
}
