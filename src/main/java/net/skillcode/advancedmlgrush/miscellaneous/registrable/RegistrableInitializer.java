package net.skillcode.advancedmlgrush.miscellaneous.registrable;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class RegistrableInitializer implements Initializer {

    @Override
    public void init(final @NotNull Injector injector) {
        injector.getInstance(RegistrableRegistry.class);
    }
}
