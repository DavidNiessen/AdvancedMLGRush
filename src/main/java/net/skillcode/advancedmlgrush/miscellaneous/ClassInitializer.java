package net.skillcode.advancedmlgrush.miscellaneous;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.DataConfig;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.config.configs.MaterialConfig;
import net.skillcode.advancedmlgrush.sql.datasavers.MLGDataSaver;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ClassInitializer {

    //Configs
    @Inject
    private DataConfig dataConfig;
    @Inject
    private ItemNameConfig itemNameConfig;
    @Inject
    private MaterialConfig materialConfig;
    //DataSavers
    @Inject
    private MLGDataSaver mlgDataSaver;

    private ClassInitializer() {
    }

    public static void initialize(@NotNull final Injector injector) {
        new ClassInitializer().init(injector);
    }

    private void init(final @NotNull Injector injector) {
        injector.injectMembers(this);
    }

}
