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

package net.skillcode.advancedmlgrush.sql;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.exception.ExceptionHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ConnectionManager {

    private final List<Connection> connections = new ArrayList<>();

    private final ExceptionHandler exceptionHandler;

    @Inject
    public ConnectionManager(final @NotNull ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void addConnection(final @Nullable Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connections.add(connection);
            }
        } catch (SQLException throwables) {
            exceptionHandler.handle(throwables);
        }
    }

    public void closeConnections() {
        connections.stream().filter(connection -> {
            try {
                return connection != null && !connection.isClosed();
            } catch (SQLException throwables) {
                exceptionHandler.handle(throwables);
            }
            return false;
        }).forEach(this::closeConnection);

        connections.clear();
    }

    private void closeConnection(final @NotNull Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            exceptionHandler.handle(throwables);
        }
    }

}
