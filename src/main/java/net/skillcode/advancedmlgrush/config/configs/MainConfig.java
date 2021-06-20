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

package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.item.GlassColor;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class MainConfig extends Configurable {

    public static final String INVENTORY_BACKGROUND_COLOR = "inventory_background_color";
    public static final String STICK_KNOCKBACK_LEVEL = "stick_knockback_level";

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.MAIN_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(INVENTORY_BACKGROUND_COLOR, GlassColor.GRAY.name()));
        list.add(new Pair<>(STICK_KNOCKBACK_LEVEL, 1));
    }
}
