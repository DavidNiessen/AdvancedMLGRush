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

    @Inject
    private ExceptionHandler exceptionHandler;

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
