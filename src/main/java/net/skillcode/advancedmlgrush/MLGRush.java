package net.skillcode.advancedmlgrush;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import net.skillcode.advancedmlgrush.config.configs.DataConfig;
import net.skillcode.advancedmlgrush.dependencyinjection.MLGBinderModule;
import net.skillcode.advancedmlgrush.sql.ConnectionManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MLGRush extends JavaPlugin {

    @Inject
    private DataConfig dataConfig;

    @Inject
    private ConnectionManager connectionManager;

    @Override
    public void onEnable() {
        final Injector injector = Guice.createInjector(new MLGBinderModule(this));
        injector.injectMembers(this);
    }

    @Override
    public void onDisable() {
        connectionManager.closeConnections();
    }
}
