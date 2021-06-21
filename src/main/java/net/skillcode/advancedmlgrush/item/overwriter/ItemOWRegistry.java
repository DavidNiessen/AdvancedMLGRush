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

package net.skillcode.advancedmlgrush.item.overwriter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.overwriter.overwriters.StatsItemOW;

@Singleton
public class ItemOWRegistry {

    @Inject
    private ItemOWManager itemOWManager;
    @Inject
    private StatsItemOW statsItemOW;

    public void registerOWs() {
        itemOWManager.registerItemOW(statsItemOW);
    }
}
