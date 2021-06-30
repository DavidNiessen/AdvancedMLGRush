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

import java.util.List;
import java.util.Optional;

@Singleton
public class MessageConfig extends Configurable implements Replaceable {

    public static final String PREFIX = "prefix";
    public static final String NO_PERMISSION = "no_permission";
    public static final String BUILD_MODE_ON = "build_mode_on";
    public static final String BUILD_MODE_OFF = "build_mode_off";
    public static final String QUEUE_JOIN = "queue_join";
    public static final String QUEUE_LEAVE = "queue_leave";
    public static final String LOADING_DATA = "loading_data";
    public static final String ERROR = "error";
    public static final String RESTART = "restart";
    public static final String CANNOT_USE_COMMAND = "cannot_use_command";
    public static final String SPAWN_SET = "spawn_set";
    public static final String CHALLENGE_1 = "challenge_1";
    public static final String CHALLENGE_2 = "challenge_2";
    public static final String ALREADY_CHALLENGED = "already_challenged";
    public static final String ALREADY_SETTING_UP_MAP = "already_setting_up_map";
    public static final String MAP_GENERATE = "map_generate";
    public static final String BREAK_OWN_BED = "break_own_bed";
    public static final String GAME_END = "game_end";
    //Command syntaxes
    public static final String SETUP_MAP_COMMAND_SYNTAX = "setup_max_command_syntax";
    //setup

    public static final String SETUP_ICON = "setup_icon";
    public static final String SETUP_FIRST_CORNER = "setup_first_corner";
    public static final String SETUP_SECOND_CORNER = "setup_second_corner";
    public static final String SETUP_MAX_BUILD_HEIGHT = "setup_max_build_height";
    public static final String SETUP_DEATH_HEIGHT = "setup_death_height";
    public static final String SETUP_SPECTATOR_SPAWN = "setup_spectator_spawn";
    public static final String SETUP_SPAWN_PLAYER_1 = "setup_spawn_player_1";
    public static final String SETUP_SPAWN_PLAYER_2 = "setup_spawn_player_2";
    public static final String SETUP_SPAWN_PLAYER_3 = "setup_spawn_player_3";
    public static final String SETUP_SPAWN_PLAYER_4 = "setup_spawn_player_4";
    public static final String SETUP_BED_PLAYER_1 = "setup_bed_player_1";
    public static final String SETUP_BED_PLAYER_2 = "setup_bed_player_2";
    public static final String SETUP_BED_PLAYER_3 = "setup_bed_player_3";
    public static final String SETUP_BED_PLAYER_4 = "setup_bed_player_4";
    public static final String SETUP_FINISH = "setup_finish";

    private final Placeholders placeholders;

    @Inject
    public MessageConfig(final Placeholders placeholders) {
        this.placeholders = placeholders;
    }

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    public String getString(final @NotNull Optional<Player> optional, final @NotNull String path) {
        return placeholders.replace(optional, super.getString(path));
    }

    public String getWithPrefix(final @NotNull Optional<Player> optionalPlayer, final @NotNull String path) {
        return getString(optionalPlayer, PREFIX) + getString(optionalPlayer, path);
    }

    @Override
    protected String filePath() {
        return Constants.MESSAGE_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(PREFIX, "&8» &6&lAdvancedMLGRush &8| &7"));
        list.add(new Pair<>(NO_PERMISSION, "&cYou don't have permission to execute this command!"));
        list.add(new Pair<>(BUILD_MODE_ON, "&aYou can build now."));
        list.add(new Pair<>(BUILD_MODE_OFF, "&cYou can no longer build now."));
        list.add(new Pair<>(QUEUE_JOIN, "&aYou entered the queue."));
        list.add(new Pair<>(QUEUE_LEAVE, "&aYou left the queue."));
        list.add(new Pair<>(LOADING_DATA, "&cYour data is being loaded, please wait."));
        list.add(new Pair<>(ERROR, "&cAn error occurred."));
        list.add(new Pair<>(RESTART, "&cThis server is restarting."));
        list.add(new Pair<>(CANNOT_USE_COMMAND, "&cYou can't use this command now."));
        list.add(new Pair<>(SPAWN_SET, "&aThe spawn has been set."));
        list.add(new Pair<>(CHALLENGE_1, "&e%player% &achallenged you &aon &e%settings_map% &a(&e%settings_rounds% rounds&a)."));
        list.add(new Pair<>(CHALLENGE_2, "&aYou challenged %player%."));
        list.add(new Pair<>(ALREADY_CHALLENGED, "&cYou already challenged &e%player%&a."));
        list.add(new Pair<>(ALREADY_SETTING_UP_MAP, "&cYou are already setting up a map."));
        list.add(new Pair<>(MAP_GENERATE, "&aGenerating map..."));
        list.add(new Pair<>(BREAK_OWN_BED, "&cYou cannot break your own bed!"));
        list.add(new Pair<>(GAME_END, "&e%winner% &awon the game."));

        list.add(new Pair<>(SETUP_MAP_COMMAND_SYNTAX, "&cWrong syntax: /setupmap <1x1, 1x4> <name>"));

        list.add(new Pair<>(SETUP_ICON, "&7Hold an item in your hand that will serve as an icon for this map and type '&e&enext&7'."));
        list.add(new Pair<>(SETUP_FIRST_CORNER, "&7Go to the lower left corner of the map and type '&enext&7'."));
        list.add(new Pair<>(SETUP_SECOND_CORNER, "&7Go to the higher right corner of the map and type '&enext&7'."));
        list.add(new Pair<>(SETUP_MAX_BUILD_HEIGHT, "&7Go to the maximum build height and type '&enext&7'."));
        list.add(new Pair<>(SETUP_DEATH_HEIGHT, "&7Go to the death height and type '&enext&7'."));
        list.add(new Pair<>(SETUP_SPECTATOR_SPAWN, "&7Go to the spectator spawn and type '&enext&7'."));
        list.add(new Pair<>(SETUP_SPAWN_PLAYER_1, "&7Go to the spawn of the first player and type '&enext&7'."));
        list.add(new Pair<>(SETUP_SPAWN_PLAYER_2, "&7Go to the spawn of the second player and type '&enext&7'."));
        list.add(new Pair<>(SETUP_SPAWN_PLAYER_3, "&7Go to the spawn of the third player and type '&enext&7'."));
        list.add(new Pair<>(SETUP_SPAWN_PLAYER_4, "&7Go to the spawn of the fourth player and type '&enext&7'."));
        list.add(new Pair<>(SETUP_BED_PLAYER_1, "&7Stand on the lower part of the first player's bed and look at the upper part. Type '&enext&7'."));
        list.add(new Pair<>(SETUP_BED_PLAYER_2, "&7Stand on the lower part of the second player's bed and look at the upper part. Type '&enext&7'."));
        list.add(new Pair<>(SETUP_BED_PLAYER_3, "&7Stand on the lower part of the third player's bed and look at the upper part. Type '&enext&7'."));
        list.add(new Pair<>(SETUP_BED_PLAYER_4, "&7Stand on the lower part of the fourth player's bed and look at the upper part. Type '&enext&7'."));
        list.add(new Pair<>(SETUP_FINISH, "&aThe setup has been finished!"));
    }

}
