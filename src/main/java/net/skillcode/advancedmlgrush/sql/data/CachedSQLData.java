package net.skillcode.advancedmlgrush.sql.data;

import lombok.Data;

@Data
public class CachedSQLData {

    public static final CachedSQLData DEFAULT_SQL_DATA = new CachedSQLData();

    private int settingsStickSlot = 0;
    private int settingsBlockSlot = 1;
    private int settingsPickaxeSlot = 8;
    private int settingsArena = -1;
    private int settingsRounds = 5;
    private int gadgetsStick = 0;
    private int gadgetsBlocks = 0;
    private int statsWins = 0;
    private int statsLoses = 0;
    private int statsBeds = 0;
    private int ranking = -1;

    public void increaseWins() {
        statsWins++;
    }

    public void increaseLoses() {
        statsLoses++;
    }

    public void increaseBeds() {
        statsBeds++;
    }


}
