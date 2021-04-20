package net.skillcode.advancedmlgrush.sql;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.exception.ExceptionHandler;
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
    private JavaPlugin plugin;
    @Inject
    private ExceptionHandler exceptionHandler;
    @Inject
    private ConnectionManager connectionManager;
    @Inject
    private ThreadPoolManager threadPoolManager;

    private DataSaverParams params;

    @Nullable
    private Connection connection;

    @PostConstruct
    public void init() {
        params = initParams();
        checkConnection();
        executeCreationQuery();
    }

    public void executeUpdateSync(final @NotNull String query) {
        if (checkConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(replaceName(query))) {
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exceptionHandler.handle(exception);
            }
        }
    }

    public void executeUpdateAsync(final @NotNull String query) {
        threadPoolManager.submit(() -> executeUpdateSync(query));
    }

    @Nullable
    public ResultSet executeQuerySync(final @NotNull String query) {
        if (checkConnection()) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(replaceName(query));
                return preparedStatement.executeQuery();
            } catch (SQLException exception) {
                exceptionHandler.handle(exception);
            }
        }
        return null;
    }

    public void executeQueryAsync(final @NotNull String query, final @NotNull Callback callback) {
        if (checkConnection()) {
            final CompletableFuture<ResultSet> future = CompletableFuture.supplyAsync(() -> executeQuerySync(query), threadPoolManager.getThreadPool());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (future.isDone()) {
                        try {
                            callback.onSuccess(future.get());
                            cancel();
                        } catch (InterruptedException | ExecutionException | SQLException e) {
                            exceptionHandler.handle(e);
                            callback.onFailure(Optional.of(e));
                        }
                    }
                }
            }.runTaskTimer(plugin, 0, params.getAsyncUpdatePeriod());
        } else {
            callback.onFailure(Optional.empty());
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException throwables) {
            exceptionHandler.handle(throwables);
        }
        return false;
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
            Class.forName("com.mysql.jdbc.Driver");
            final String url = "jdbc:mysql://" + params.getHost() + ":" + params.getPort() + "/" + params.getDatabase();

            return DriverManager.getConnection(url, params.getUser(), params.getPassword());
        } catch (SQLException | ClassNotFoundException throwables) {
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

    private String replaceName(final @NotNull String query) {
        return query.replace("{name}", params.getTable());
    }

    public interface Callback {

        void onSuccess(final @Nullable ResultSet resultSet) throws SQLException;

        void onFailure(final @NotNull Optional<Exception> optional);

    }
}
