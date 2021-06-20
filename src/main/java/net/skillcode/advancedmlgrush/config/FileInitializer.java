package net.skillcode.advancedmlgrush.config;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.*;
import net.skillcode.advancedmlgrush.game.gadgets.GadgetManager;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class FileInitializer implements Initializer {

    @Override
    public void init(final @NotNull Injector injector) {
        injector.getInstance(MainConfig.class);
        injector.getInstance(DataConfig.class);
        injector.getInstance(MessageConfig.class);
        injector.getInstance(ItemNameConfig.class);
        injector.getInstance(ItemMaterialConfig.class);
        injector.getInstance(SoundConfig.class);
        injector.getInstance(InventoryNameConfig.class);
        injector.getInstance(DebugConfig.class);
        injector.getInstance(GadgetManager.class);
    }
}
