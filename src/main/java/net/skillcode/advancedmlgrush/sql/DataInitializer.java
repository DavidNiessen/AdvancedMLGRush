package net.skillcode.advancedmlgrush.sql;

import com.google.inject.Injector;
import net.skillcode.advancedmlgrush.sql.datasavers.MLGDataSaver;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

public class DataInitializer implements Initializer {

    @Override
    public void init(final @NotNull Injector injector) {
        injector.getInstance(MLGDataSaver.class);
    }
}
