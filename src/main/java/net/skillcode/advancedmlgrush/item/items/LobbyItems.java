package net.skillcode.advancedmlgrush.item.items;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.ItemManager;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class LobbyItems {

    @Inject
    private ItemManager itemManager;

    public void setLobbyItems(final @NotNull Player player) {
        player.getInventory().setItem(Constants.CHALLENGER_SLOT, itemManager.getItem(Optional.of(player), EnumItem.CHALLENGER));
        player.getInventory().setItem(Constants.SETTINGS_SLOT, itemManager.getItem(Optional.of(player), EnumItem.SETTINGS));
        player.getInventory().setItem(Constants.SPECTATE_SLOT, itemManager.getItem(Optional.of(player), EnumItem.SPECTATE));
        player.getInventory().setItem(Constants.EXTRAS_SLOT, itemManager.getItem(Optional.of(player), EnumItem.EXTRAS));
        player.getInventory().setItem(Constants.STATS_SLOT, itemManager.getItem(Optional.of(player), EnumItem.STATS));
    }
}
