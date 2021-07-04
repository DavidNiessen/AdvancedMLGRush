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

package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.placeholder.Placeholders;
import net.skillcode.advancedmlgrush.placeholder.Replaceable;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class ScoreboardConfig extends Configurable implements Replaceable {

    public static final String SCOREBOARD_TITLE = "scoreboard_title";
    public static final String LOBBY_SCOREBOARD = "lobby_scoreboard";
    public static final String MAP_1X1_SCOREBOARD = "map_1x1_scoreboard";
    public static final String MAP_1X4_SCOREBOARD = "map_1x4_scoreboard";

    private final Placeholders placeholders;

    @Inject
    public ScoreboardConfig(final @NotNull Placeholders placeholders) {
        this.placeholders = placeholders;
    }

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.SCOREBOARD_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(SCOREBOARD_TITLE, "  &8» &6&lMLGRush  "));
        list.add(new Pair<>(LOBBY_SCOREBOARD, new ArrayList<>(Arrays.asList(
                "&1",
                "&7Wins&8:",
                "&8» &e%stats_wins%",
                "&2",
                "&7Loses&8:",
                "&8» &e%stats_loses%",
                "&3",
                "&7Ranking&8:",
                "&8» &e#%stats_ranking%",
                "&4"
        ))));
        list.add(new Pair<>(MAP_1X1_SCOREBOARD, new ArrayList<>(Arrays.asList(
                "&1",
                "&7Map&8:",
                "&8» &e%map_name% &7(&e%map_mode%&7)",
                "&3",
                "&7Scores&8:",
                "&8» &e&l%map_score_1% &8| &e%map_player_1%",
                "&8» &e&l%map_score_2% &8| &e%map_player_2%",
                "&2"
        ))));
        list.add(new Pair<>(MAP_1X4_SCOREBOARD, new ArrayList<>(Arrays.asList(
                "&1",
                "&7Map&8:",
                "&8» &e%map_name% &7(&e%map_mode%&7)",
                "&3",
                "&7Scores&8:",
                "&8» &e&l%map_score_1% &8| &e%map_player_1%",
                "&8» &e&l%map_score_2% &8| &e%map_player_2%",
                "&8» &e&l%map_score_3% &8| &e%map_player_3%",
                "&8» &e&l%map_score_4% &8| &e%map_player_4%",
                "&4"
        ))));
    }

    @Override
    public String getString(final @NotNull Optional<Player> optionalPlayer, final @NotNull String path) {
        return placeholders.replace(optionalPlayer, getString(path));
    }
}
