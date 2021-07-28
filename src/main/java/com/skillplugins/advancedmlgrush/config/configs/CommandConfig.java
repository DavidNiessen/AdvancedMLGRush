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

package com.skillplugins.advancedmlgrush.config.configs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.Configurable;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Singleton
public class CommandConfig extends Configurable {

    public static final String WIN = "win";
    public static final String LOSE = "lose";
    public static final String KILL = "kill";
    public static final String DEATH = "death";
    public static final String BED_BREAK = "bed_break";
    public static final String OWN_BED_BROKEN = "own_bed_broken";

    private final Placeholders placeholders;

    @Inject
    public CommandConfig(final Placeholders placeholders) {
        this.placeholders = placeholders;
    }


    @PostConstruct
    public void initConfig() {
        super.init();
    }

    public void runCommand(final String path, final Optional<Player> optionalPlayer) {
        final String command = super.getString(path);
        if (!command.toLowerCase(Locale.ROOT).equals("none")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    placeholders.replace(optionalPlayer, command));
        }
    }

    @Override
    protected String filePath() {
        return Constants.COMMAND_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(WIN, "none"));
        list.add(new Pair<>(LOSE, "none"));
        list.add(new Pair<>(KILL, "none"));
        list.add(new Pair<>(DEATH, "none"));
        list.add(new Pair<>(BED_BREAK, "none"));
        list.add(new Pair<>(OWN_BED_BROKEN, "none"));
    }
}
