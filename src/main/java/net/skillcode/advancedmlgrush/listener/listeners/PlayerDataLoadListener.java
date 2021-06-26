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

package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.event.events.PlayerDataLoadEvent;
import net.skillcode.advancedmlgrush.game.gadgets.GadgetManager;
import net.skillcode.advancedmlgrush.game.rounds.RoundManager;
import net.skillcode.advancedmlgrush.sql.data.CachedSQLData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class PlayerDataLoadListener implements Listener {

    @Inject
    private EventManager eventManager;
    @Inject
    private GadgetManager gadgetManager;
    @Inject
    private RoundManager roundManager;
    @Inject
    private MainConfig mainConfig;

    @EventHandler
    public void onLoad(final @NotNull PlayerDataLoadEvent event) {
        eventManager.callEvent(event);

        final Player player = event.getPlayer();
        final CachedSQLData cachedSQLData = event.getCachedSQLData();

        if (!player.hasPermission(gadgetManager.getStick(player).getPermission())) {
            cachedSQLData.setGadgetsStick(0);
        }
        if (!player.hasPermission(gadgetManager.getBlock(player).getPermission())) {
            cachedSQLData.setGadgetsBlocks(0);
        }

        final List<Integer> rounds = roundManager.getRoundList();
        final int defaultRounds = mainConfig.getInt(MainConfig.DEFAULT_ROUNDS);

        if (!rounds.isEmpty()
                && !rounds.contains(cachedSQLData.getSettingsRounds())
                && cachedSQLData.getSettingsRounds() != defaultRounds) {
            cachedSQLData.setSettingsRounds(mainConfig.getInt(MainConfig.DEFAULT_ROUNDS));
        }
    }

}
