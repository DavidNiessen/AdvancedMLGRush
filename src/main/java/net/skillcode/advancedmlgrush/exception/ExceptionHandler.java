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

package net.skillcode.advancedmlgrush.exception;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.DebugConfig;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ExceptionHandler {

    private final JavaPlugin javaPlugin;
    private final Provider<DebugConfig> debugConfigProvider;

    private DebugConfig debugConfig;

    @Inject
    public ExceptionHandler(final @NotNull JavaPlugin javaPlugin, final @NotNull Provider<DebugConfig> debugConfigProvider) {
        this.javaPlugin = javaPlugin;
        this.debugConfigProvider = debugConfigProvider;
    }

    @PostConstruct
    public void init() {
        debugConfig = debugConfigProvider.get();
    }

    public void handle(final @NotNull Exception exception) {
        if (debugConfig.getBoolean(DebugConfig.LOG_STACKTRACES)) {
            exception.printStackTrace();
        } else {
            javaPlugin.getLogger().warning(String.format(Constants.ERROR_MESSAGE, exception.getClass().getSimpleName()));
        }
    }

}
