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

package com.skillplugins.advancedmlgrush.config;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.*;
import com.skillplugins.advancedmlgrush.game.gadgets.GadgetManager;
import com.skillplugins.advancedmlgrush.game.spawn.SpawnFileLoader;
import com.skillplugins.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class FileInitializer implements Initializer {

    @Override
    public void init(final @NotNull Injector injector) {
        injector.getInstance(MainConfig.class);
        injector.getInstance(DataConfig.class);
        injector.getInstance(MessageConfig.class);
        injector.getInstance(ItemNameConfig.class);
        injector.getInstance(ItemMaterialConfig.class);
        injector.getInstance(SoundConfig.class);
        injector.getInstance(InventoryNameConfig.class);
        injector.getInstance(DebugConfig.class);
        injector.getInstance(GadgetManager.class);
        injector.getInstance(SpawnFileLoader.class);
        injector.getInstance(ScoreboardConfig.class);
        injector.getInstance(SlotsConfig.class);
        injector.getInstance(CommandConfig.class);
    }
}
