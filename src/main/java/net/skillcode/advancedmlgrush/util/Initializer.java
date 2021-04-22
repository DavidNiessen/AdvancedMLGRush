package net.skillcode.advancedmlgrush.util;

import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;

public interface Initializer {

    void init(final @NotNull Injector injector);

}
