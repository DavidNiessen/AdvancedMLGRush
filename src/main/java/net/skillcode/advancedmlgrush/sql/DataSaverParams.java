package net.skillcode.advancedmlgrush.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataSaverParams {

    //config values
    private final boolean mySQL;
    private final String host;
    private final int port;
    private final String database;
    private final String user;
    private final String password;
    private final long asyncUpdatePeriod;
    //other values
    private final String table;
    private final String dataFilePath;

}
