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

package net.skillcode.advancedmlgrush.config;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.*;
import net.skillcode.advancedmlgrush.game.gadgets.GadgetManager;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFileLoader;
import net.skillcode.advancedmlgrush.util.Initializer;
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
    }
}
