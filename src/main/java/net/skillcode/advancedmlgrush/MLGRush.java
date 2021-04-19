package net.skillcode.advancedmlgrush;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.skillcode.advancedmlgrush.dependencyinjection.MLGBinderModule;
import org.bukkit.plugin.java.JavaPlugin;

public class MLGRush extends JavaPlugin {

    @Override
    public void onEnable() {
        final Injector injector = Guice.createInjector(new MLGBinderModule(this));

    }
}
