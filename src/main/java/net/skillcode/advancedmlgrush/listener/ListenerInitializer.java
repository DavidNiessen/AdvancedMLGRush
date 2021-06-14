package net.skillcode.advancedmlgrush.listener;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.listener.listeners.AsyncPlayerChatListener;
import net.skillcode.advancedmlgrush.listener.listeners.PlayerInteractListener;
import net.skillcode.advancedmlgrush.listener.listeners.PlayerJoinListener;
import net.skillcode.advancedmlgrush.listener.listeners.PlayerQuitListener;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ListenerInitializer implements Initializer {

    private final JavaPlugin plugin;
    private final PluginManager pluginManager;

    @Inject
    public ListenerInitializer(final @NotNull JavaPlugin plugin, final @NotNull PluginManager pluginManager) {
        this.plugin = plugin;
        this.pluginManager = pluginManager;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        pluginManager.registerEvents(injector.getInstance(PlayerJoinListener.class), plugin);
        pluginManager.registerEvents(injector.getInstance(PlayerQuitListener.class), plugin);
        pluginManager.registerEvents(injector.getInstance(PlayerInteractListener.class), plugin);
        pluginManager.registerEvents(injector.getInstance(AsyncPlayerChatListener.class), plugin);
    }

}
