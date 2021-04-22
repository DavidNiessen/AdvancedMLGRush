package net.skillcode.advancedmlgrush.miscellaneous.registrable;

import com.google.inject.Injector;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

public class RegistrableInitializer implements Initializer {

    @Override
    public void init(final @NotNull Injector injector) {
        injector.getInstance(RegistrableRegistry.class);
    }
}
