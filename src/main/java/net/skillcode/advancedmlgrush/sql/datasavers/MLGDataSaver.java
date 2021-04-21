package net.skillcode.advancedmlgrush.sql.datasavers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.DataConfig;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.sql.DataSaver;
import net.skillcode.advancedmlgrush.sql.DataSaverParams;

@Singleton
public class MLGDataSaver extends DataSaver {

    @Inject
    private DataConfig dataConfig;

    @Override
    protected DataSaverParams initParams() {
        return new DataSaverParams(
                dataConfig.getBoolean(DataConfig.USE_MYSQL), dataConfig.getString(DataConfig.MYSQL_HOST),
                dataConfig.getString(DataConfig.MYSQL_PORT), dataConfig.getString(DataConfig.MYSQL_DATABASE),
                dataConfig.getString(DataConfig.MYSQL_USER), dataConfig.getString(DataConfig.MYSQL_PASSWORD),
                dataConfig.getLong(DataConfig.ASYNC_UPDATE_PERIOD), Constants.TABLE_NAME, Constants.DATABASE_PATH);
    }

    @Override
    protected String creationQuery() {
        return "CREATE TABLE IF NOT EXISTS mlgrush (\n" +
                "    player_uuid VARCHAR(36) NOT NULL,\n" +
                "    player_name VARCHAR(100) NOT NULL,\n" +
                "    settings_stick_slot TINYINT NOT NULL DEFAULT 0,\n" +
                "    settings_block_slot TINYINT NOT NULL DEFAULT 1,\n" +
                "    settings_pickaxe_slot TINYINT NOT NULL DEFAULT 8,\n" +
                "    settings_arena TINYINT NOT NULL DEFAULT -1,\n" +
                "    extras_stick TINYINT NOT NULL DEFAULT 0,\n" +
                "    extras_blocks TINYINT NOT NULL DEFAULT 0,\n" +
                "    stats_wins TINYINT NOT NULL DEFAULT 0,\n" +
                "    stats_loses TINYINT NOT NULL DEFAULT 0,\n" +
                "    stats_beds TINYINT NOT NULL DEFAULT 0,\n" +
                "    PRIMARY KEY (player_uuid, player_name)\n" +
                ")";
    }
}
