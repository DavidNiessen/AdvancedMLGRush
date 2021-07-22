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
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import com.skillplugins.advancedmlgrush.placeholder.Replaceable;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class ItemNameConfig extends Configurable implements Replaceable {

    private final Placeholders placeholders;

    @Inject
    public ItemNameConfig(final Placeholders placeholders) {
        this.placeholders = placeholders;
    }

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    public String getString(final @NotNull Optional<Player> optionalPlayer, final @NotNull String path) {
        return placeholders.replace(optionalPlayer, super.getString(path));
    }

    public String getString(final @NotNull Optional<Player> optionalPlayer, final @NotNull EnumItem enumItem) {
        return getString(optionalPlayer, enumItem.getConfigPath());
    }

    @Override
    protected String filePath() {
        return Constants.ITEM_NAME_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(EnumItem.INVENTORY_BACKGROUND.getConfigPath(), " "));
        list.add(new Pair<>(EnumItem.CHALLENGER.getConfigPath(), "&8» &eChallenge Player"));
        list.add(new Pair<>(EnumItem.SETTINGS.getConfigPath(), "&8» &eSettings"));
        list.add(new Pair<>(EnumItem.SPECTATE.getConfigPath(), "&8» &eSpectate"));
        list.add(new Pair<>(EnumItem.GADGETS.getConfigPath(), "&8» &eGadgets"));
        list.add(new Pair<>(EnumItem.STATS.getConfigPath(), "&8» &eStats"));
        list.add(new Pair<>(EnumItem.PICKAXE.getConfigPath(), "&8» &ePickaxe"));
        list.add(new Pair<>(EnumItem.QUEUE_LEAVE.getConfigPath(), "&8» &cLeave Queue"));
        list.add(new Pair<>(EnumItem.ARROW_LEFT.getConfigPath(), "&e«"));
        list.add(new Pair<>(EnumItem.ARROW_RIGHT.getConfigPath(), "&e»"));
        list.add(new Pair<>(EnumItem.RANDOM_ITEM.getConfigPath(), "&8» &eRandom Map"));
        list.add(new Pair<>(EnumItem.SETTINGS_INVENTORY_SORTING.getConfigPath(), "&8» &eInventory Sorting"));
        list.add(new Pair<>(EnumItem.SETTINGS_MAP.getConfigPath(), "&8» &eMap"));
        list.add(new Pair<>(EnumItem.SETTINGS_ROUNDS.getConfigPath(), "&8» &eRounds"));
        list.add(new Pair<>(EnumItem.GADGETS_STICK.getConfigPath(), "&8» &eStick"));
        list.add(new Pair<>(EnumItem.GADGETS_BLOCKS.getConfigPath(), "&8» &eBlocks"));
        list.add(new Pair<>(EnumItem.SORTING_SAVE.getConfigPath(), "&8» &aSave"));
        list.add(new Pair<>(EnumItem.SORTING_RESET.getConfigPath(), "&8» &cReset"));
        list.add(new Pair<>(EnumItem.ROUNDS_INCREASE.getConfigPath(), "&e+"));
        list.add(new Pair<>(EnumItem.ROUNDS_DECREASE.getConfigPath(), "&e-"));
        list.add(new Pair<>(EnumItem.ROUNDS.getConfigPath(), "&8» &e%settings_rounds% &8«"));
        list.add(new Pair<>(EnumItem.STATS_WINS.getConfigPath(), "&8» &eWins &8| &e&l%stats_wins%"));
        list.add(new Pair<>(EnumItem.STATS_LOSES.getConfigPath(), "&8» &eLoses &8| &e&l%stats_loses%"));
        list.add(new Pair<>(EnumItem.STATS_WIN_RATE.getConfigPath(), "&8» &eWin Rate &8| &e&l%stats_win_rate%"));
        list.add(new Pair<>(EnumItem.STATS_BEDS.getConfigPath(), "&8» &eBeds &8| &e&l%stats_beds%"));
        list.add(new Pair<>(EnumItem.STATS_RANKING.getConfigPath(), "&8» &eRanking &8| &e&l#%stats_ranking%"));
        list.add(new Pair<>(EnumItem.STATS_KILLS.getConfigPath(), "&8» &eKills &8| &e&l%stats_kills%"));
        list.add(new Pair<>(EnumItem.STATS_DEATHS.getConfigPath(), "&8» &eDeaths &8| &e&l%stats_deaths%"));
        list.add(new Pair<>(EnumItem.STATS_PLACED_BLOCKS.getConfigPath(), "&8» &ePlaced Blocks &8| &e&l%stats_placed_blocks%"));
        list.add(new Pair<>(EnumItem.QUEUE_2X1.getConfigPath(), "&8» &e2x1 &7(&e%queue_2x1%&7)"));
        list.add(new Pair<>(EnumItem.QUEUE_4X1.getConfigPath(), "&8» &e4x1 &7(&e%queue_4x1%&7)"));
        list.add(new Pair<>(EnumItem.SPECTATE_LEAVE.getConfigPath(), "&8» &eLeave"));
        list.add(new Pair<>(EnumItem.MAP.getConfigPath(), "&8» &e%map_name%"));
    }

}
