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

package net.skillcode.advancedmlgrush.event.events;

import lombok.Getter;
import net.skillcode.advancedmlgrush.sql.data.CachedSQLData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerDataLoadEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private final boolean defaultData;
    private final CachedSQLData cachedSQLData;

    public PlayerDataLoadEvent(final @NotNull Player who,
                               final boolean defaultData,
                               final @NotNull CachedSQLData cachedSQLData) {
        super(who);
        this.defaultData = defaultData;
        this.cachedSQLData = cachedSQLData;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
