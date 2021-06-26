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

package net.skillcode.advancedmlgrush.game.rounds;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import net.skillcode.advancedmlgrush.sql.data.CachedSQLData;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class RoundManager implements Registrable {

    @Getter
    private final List<Integer> roundList = new CopyOnWriteArrayList<>();
    private final Map<Player, Integer> roundMap = new ConcurrentHashMap<>();

    @Inject
    private MainConfig mainConfig;
    @Inject
    private SQLDataCache sqlDataCache;

    @PostConstruct
    public void init() {
        roundList.addAll(mainConfig.getIntegerList(MainConfig.ROUNDS));
        Collections.sort(roundList);
    }

    public boolean increaseRounds(final @NotNull Player player) {
        final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);
        int rounds = getRoundsFromMap(player, cachedSQLData);

        if (roundList.contains(rounds)) {
            final int index = roundList.indexOf(rounds);
            if (index != roundList.size() - 1) {
                rounds = roundList.get(index + 1);
                roundMap.put(player, rounds);
                cachedSQLData.setSettingsRounds(rounds);
                return true;
            }
        }
        return false;
    }

    public boolean decreaseRounds(final @NotNull Player player) {
        final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);
        int rounds = getRoundsFromMap(player, cachedSQLData);

        if (roundList.contains(rounds)) {
            final int index = roundList.indexOf(rounds);
            if (index != 0) {
                rounds = roundList.get(index - 1);
                roundMap.put(player, rounds);
                cachedSQLData.setSettingsRounds(rounds);
                return true;
            }
        }
        return false;
    }

    @Override
    public void unregister(final @NotNull Player player) {
        roundMap.remove(player);
    }

    private int getRoundsFromMap(final @NotNull Player player, final @NotNull CachedSQLData cachedSQLData) {
        int rounds;
        if (roundMap.containsKey(player)) {
            rounds = roundMap.get(player);
        } else {
            rounds = cachedSQLData.getSettingsRounds();
            roundMap.put(player, rounds);
        }
        return rounds;
    }
}
