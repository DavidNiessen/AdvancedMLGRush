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

package net.skillcode.advancedmlgrush.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.command.commands.BuildCommand;
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
    }
}
