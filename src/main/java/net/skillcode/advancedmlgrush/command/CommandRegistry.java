package net.skillcode.advancedmlgrush.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import net.skillcode.advancedmlgrush.command.commands.BuildCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CommandRegistry {

    private final JavaPlugin javaPlugin;

    @Inject
    public CommandRegistry(final @NotNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public void registerCommands(final @NotNull Injector injector) {
        javaPlugin.getCommand("build").setExecutor(injector.getInstance(BuildCommand.class));
    }
}
