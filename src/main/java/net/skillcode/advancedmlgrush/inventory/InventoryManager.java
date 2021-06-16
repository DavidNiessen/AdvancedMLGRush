package net.skillcode.advancedmlgrush.inventory;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InventoryManager implements Registrable {

    private final Map<Player, Class<? extends AbstractInventory>> inventoryMap = new ConcurrentHashMap<>();

    public void register(final @NotNull Player player, final @NotNull Class<? extends AbstractInventory> inventoryClass) {
        inventoryMap.put(player, inventoryClass);
    }

    @Override
    public void unregister(final @NotNull Player player) {
        inventoryMap.remove(player);
    }

    public Optional<Class<? extends AbstractInventory>> getOpenInventory(final @NotNull Player player) {
        return Optional.ofNullable(inventoryMap.getOrDefault(player, null));
    }
}
