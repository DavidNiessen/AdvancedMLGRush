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

package com.skillplugins.advancedmlgrush.sql.data;

import lombok.Data;

@Data
public class CachedSQLData {

    public static final CachedSQLData DEFAULT_SQL_DATA = new CachedSQLData();

    private boolean defaultData = true;

    private int settingsStickSlot = 0;
    private int settingsBlockSlot = 1;
    private int settingsPickaxeSlot = 8;
    private int settingsMap = 0;
    private int settingsRounds = 5;
    private int gadgetsStick = 0;
    private int gadgetsBlocks = 0;
    private int statsWins = 0;
    private int statsLoses = 0;
    private int statsBeds = 0;
    private int statsKills = 0;
    private int statsDeaths = 0;

    public void increaseWins() {
        statsWins++;
    }

    public void increaseLoses() {
        statsLoses++;
    }

    public void increaseBeds() {
        statsBeds++;
    }

    public void increaseKills() {
        statsKills++;
    }

    public void increaseDeaths() {
        statsDeaths++;
    }


}
