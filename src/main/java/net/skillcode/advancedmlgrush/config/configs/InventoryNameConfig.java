package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.placeholder.Placeholders;
import net.skillcode.advancedmlgrush.placeholder.Replaceable;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class InventoryNameConfig extends Configurable implements Replaceable {

    private final Placeholders placeholders;

    @Inject
    public InventoryNameConfig(final Placeholders placeholders) {
        this.placeholders = placeholders;
    }

    public static final String SETTINGS = "settings";
    public static final String STICK = "stick";
    public static final String BLOCKS = "blocks";

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    public String getString(final @NotNull Optional<Player> optionalPlayer, final @NotNull String path) {
        return placeholders.replace(optionalPlayer, super.getString(path));
    }

    @Override
    protected String filePath() {
        return Constants.INVENTORY_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(SETTINGS, "&8» &eSettings"));
        list.add(new Pair<>(STICK, "&8» &eStick"));
        list.add(new Pair<>(BLOCKS, "&8» &eBlocks"));
    }
}
