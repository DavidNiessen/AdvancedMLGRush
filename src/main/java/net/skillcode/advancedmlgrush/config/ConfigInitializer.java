package net.skillcode.advancedmlgrush.config;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.*;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ConfigInitializer implements Initializer {

    @Override
    public void init(final @NotNull Injector injector) {
        injector.getInstance(MainConfig.class);
        injector.getInstance(DataConfig.class);
        injector.getInstance(MessageConfig.class);
        injector.getInstance(ItemNameConfig.class);
        injector.getInstance(MaterialConfig.class);
    }
}
