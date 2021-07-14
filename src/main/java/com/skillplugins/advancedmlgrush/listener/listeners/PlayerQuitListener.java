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

package com.skillplugins.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.event.EventManager;
import com.skillplugins.advancedmlgrush.miscellaneous.registrable.RegistrableManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PlayerQuitListener implements Listener {

    private final RegistrableManager registrableManager;
    private final EventManager eventManager;

    @Inject
    public PlayerQuitListener(final @NotNull RegistrableManager registrableManager,
                              final @NotNull EventManager eventManager) {
        this.registrableManager = registrableManager;
        this.eventManager = eventManager;
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        event.setQuitMessage("");
        final Player player = event.getPlayer();
        eventManager.callEvent(event);

        registrableManager.unregisterPlayer(player);
    }

}
