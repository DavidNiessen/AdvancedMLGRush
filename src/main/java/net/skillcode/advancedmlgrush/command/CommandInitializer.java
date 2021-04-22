package net.skillcode.advancedmlgrush.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.command.commands.BuildCommand;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Singleton
public class CommandInitializer implements Initializer {

    private final JavaPlugin javaPlugin;

    @Inject
    public CommandInitializer(final @NotNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        javaPlugin.getCommand("build").setExecutor(injector.getInstance(BuildCommand.class));
    }
}
