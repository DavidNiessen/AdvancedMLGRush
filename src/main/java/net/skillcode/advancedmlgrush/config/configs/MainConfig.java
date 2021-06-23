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

package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class MainConfig extends Configurable {

    public static final String STICK_KNOCKBACK_LEVEL = "stick_knockback_level";
    public static final String PICKAXE_EFFICIENCY_LEVEL = "pickaxe_efficiency_level";
    public static final String PLACEHOLDER_NULL_VALUE = "placeholder_null_value";
    public static final String PLACEHOLDER_LOADING_VALUE = "placeholder_loading_value";
    public static final String ROUNDS = "rounds";
    public static final String DEFAULT_ROUNDS = "default_rounds";
    public static final String RANKING_UPDATE_PERIOND = "ranking_update_period";
    public static final String STATS_ITEM_LORE = "stats_item_lore";

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
        list.add(new Pair<>(STICK_KNOCKBACK_LEVEL, 1));
        list.add(new Pair<>(PICKAXE_EFFICIENCY_LEVEL, 1));
        list.add(new Pair<>(PLACEHOLDER_NULL_VALUE, "-"));
        list.add(new Pair<>(PLACEHOLDER_LOADING_VALUE, "Loading..."));
        list.add(new Pair<>(ROUNDS, new ArrayList<>(Arrays.asList(3, 5, 10, 15))));
        list.add(new Pair<>(DEFAULT_ROUNDS, 5));
        list.add(new Pair<>(RANKING_UPDATE_PERIOND, 5.0));
        list.add(new Pair<>(STATS_ITEM_LORE, new ArrayList<>(Arrays.asList(
                " ",
                "&6&l#1 &8» &e%ranking_1%",
                "&7&l#2 &8» &e%ranking_2%",
                "&c&l#3 &8» &e%ranking_3%"
        ))));
    }
}
