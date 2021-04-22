package net.skillcode.advancedmlgrush.miscellaneous.registrable;

import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class RegistrableManager {

    private final List<Registrable> registrables = new CopyOnWriteArrayList<>();

    public void registerRegistrable(@NotNull final Registrable registrable) {
        if (!registrables.contains(registrable)) {
            registrables.add(registrable);
        }
    }

    public void unregisterPlayer(final @NotNull Player player) {
        registrables.forEach(registrable -> registrable.unregister(player));
    }

}
