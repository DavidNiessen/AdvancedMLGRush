package net.skillcode.advancedmlgrush.sql;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.exception.ExceptionHandler;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.*;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public abstract class DataSaver {

    @Inject
    protected ExceptionHandler exceptionHandler;
    @Inject
    protected ThreadPoolManager threadPoolManager;
    @Inject
    private JavaPlugin plugin;
    @Inject
    private ConnectionManager connectionManager;

    private DataSaverParams params;

    @Nullable
    private Connection connection;

    @PostConstruct
    public void init() {
        params = initParams();
        checkConnection();
        executeCreationQuery();
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException throwables) {
            exceptionHandler.handle(throwables);
        }
        return false;
    }

    protected void executeUpdateSync(final @NotNull String query) {
        if (checkConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(replaceName(query))) {
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exceptionHandler.handle(exception);
            }
        }
    }

    protected void executeUpdateAsync(final @NotNull String query) {
        threadPoolManager.submit(() -> executeUpdateSync(query));
    }

    protected Optional<ResultSet> executeQuerySync(final @NotNull String query) {
        System.out.println(query);
        if (checkConnection()) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(replaceName(query));
                final ResultSet resultSet = preparedStatement.executeQuery();
                return Optional.of(resultSet);
            } catch (SQLException exception) {
                exceptionHandler.handle(exception);
            }
        }
        return Optional.empty();
    }

    protected abstract DataSaverParams initParams();

    protected abstract String creationQuery();

    private boolean checkConnection() {
        if (!isConnected()) {
            createConnection();
        }
        return isConnected();
    }

    private void executeCreationQuery() {
        executeUpdateSync(creationQuery());
    }

    private void createConnection() {
        connection = params.isMySQL() ? createMySQLConnection() : createSQLiteConnection();
        connectionManager.addConnection(connection);
    }

    @Nullable
    private Connection createMySQLConnection() {
        try {
            final String url = "jdbc:mysql://" + params.getHost() + ":" + checkPort(params.getPort()) + "/" + params.getDatabase();

            return DriverManager.getConnection(url, params.getUser(), params.getPassword());
        } catch (SQLException throwables) {
            exceptionHandler.handle(throwables);
        }
        return null;
    }

    @Nullable
    private Connection createSQLiteConnection() {
        try {
            Class.forName("org.sqlite.JDBC");

            final File file = new File(params.getDataFilePath());
            file.getParentFile().mkdirs();

            return DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
        } catch (SQLException | ClassNotFoundException throwables) {
            exceptionHandler.handle(throwables);
        }
        return null;
    }

    private String checkPort(final @NotNull String port) {
        String finalPort = port;
        try {
            Integer.parseInt(port);
        } catch (final NumberFormatException exception) {
            finalPort = "3306";
            plugin.getLogger().warning(Constants.INVALID_PORT_MESSAGE);
        }
        return finalPort;
    }

    private String replaceName(final @NotNull String query) {
        return query.replace("{name}", params.getTable());
    }

    public interface Callback {

        void onSuccess(final @NotNull Optional<ResultSet> optional) throws SQLException;

        void onFailure(final @NotNull Optional<Exception> optional);

    }
}
