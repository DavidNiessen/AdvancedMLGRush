package net.skillcode.advancedmlgrush.config;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.DataConfig;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.config.configs.MaterialConfig;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ConfigInitializer implements Initializer {

    @Override
    public void init(final @NotNull Injector injector) {
        injector.getInstance(DataConfig.class);
        injector.getInstance(MessageConfig.class);
        injector.getInstance(ItemNameConfig.class);
        injector.getInstance(MaterialConfig.class);
    }
}
