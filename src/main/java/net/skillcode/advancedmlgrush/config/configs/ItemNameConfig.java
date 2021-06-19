package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.placeholder.Placeholders;
import net.skillcode.advancedmlgrush.placeholder.Replaceable;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class ItemNameConfig extends Configurable implements Replaceable {

    private final Placeholders placeholders;

    @Inject
    public ItemNameConfig(final Placeholders placeholders) {
        this.placeholders = placeholders;
    }

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    public String getString(final @NotNull Optional<Player> optionalPlayer, final @NotNull String path) {
        return placeholders.replace(optionalPlayer, super.getString(path));
    }

    public String getString(final @NotNull Optional<Player> optionalPlayer, final @NotNull EnumItem enumItem) {
        return getString(optionalPlayer, enumItem.getConfigPath());
    }

    @Override
    protected String filePath() {
        return Constants.ITEM_NAME_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(EnumItem.CHALLENGER.getConfigPath(), "&8» &eChallenge player"));
        list.add(new Pair<>(EnumItem.SETTINGS.getConfigPath(), "&8» &eSettings"));
        list.add(new Pair<>(EnumItem.SPECTATE.getConfigPath(), "&8» &eSpectate"));
        list.add(new Pair<>(EnumItem.EXTRAS.getConfigPath(), "&8» &eExtras"));
        list.add(new Pair<>(EnumItem.STATS.getConfigPath(), "&8» &eStats"));
        list.add(new Pair<>(EnumItem.QUEUE_LEAVE.getConfigPath(), "&8» &cLeave queue"));
        list.add(new Pair<>(EnumItem.INVENTORY_BACKGROUND.getConfigPath(), " "));
        list.add(new Pair<>(EnumItem.ARROW_LEFT.getConfigPath(), "&e«"));
        list.add(new Pair<>(EnumItem.ARROW_RIGHT.getConfigPath(), "&e»"));
        list.add(new Pair<>(EnumItem.SETTINGS_INVENTORY_SORTING.getConfigPath(), "&8» &eInventory Sorting"));
        list.add(new Pair<>(EnumItem.SETTINGS_MAP.getConfigPath(), "&8» &eMap"));
        list.add(new Pair<>(EnumItem.SETTINGS_STICK.getConfigPath(), "&8» &eStick"));
        list.add(new Pair<>(EnumItem.SETTINGS_BLOCKS.getConfigPath(), "&8» &eBlocks"));
    }

}
