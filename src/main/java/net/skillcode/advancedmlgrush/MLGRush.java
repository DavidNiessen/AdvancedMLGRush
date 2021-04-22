package net.skillcode.advancedmlgrush;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import net.skillcode.advancedmlgrush.command.CommandInitializer;
import net.skillcode.advancedmlgrush.config.ConfigInitializer;
import net.skillcode.advancedmlgrush.dependencyinjection.MLGBinderModule;
import net.skillcode.advancedmlgrush.event.EventHandlerInitializer;
import net.skillcode.advancedmlgrush.listener.ListenerInitializer;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.RegistrableInitializer;
import net.skillcode.advancedmlgrush.sql.ConnectionManager;
import net.skillcode.advancedmlgrush.sql.DataInitializer;
import org.bukkit.plugin.java.JavaPlugin;

public class MLGRush extends JavaPlugin {

    @Inject
    private DataInitializer dataInitializer;
    @Inject
    private ConfigInitializer configInitializer;
    @Inject
    private ListenerInitializer listenerInitializer;
    @Inject
    private CommandInitializer commandInitializer;
    @Inject
    private RegistrableInitializer registrableInitializer;
    @Inject
    private EventHandlerInitializer eventHandlerInitializer;
    @Inject
    private ConnectionManager connectionManager;

    @Override
    public void onEnable() {
        final Injector injector = Guice.createInjector(new MLGBinderModule(this));
        injector.injectMembers(this);

        dataInitializer.init(injector);
        configInitializer.init(injector);
        registrableInitializer.init(injector);
        listenerInitializer.init(injector);
        eventHandlerInitializer.init(injector);
        commandInitializer.init(injector);
    }

    @Override
    public void onDisable() {
        connectionManager.closeConnections();
    }

}
