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
public class ItemNameConfig extends Configurable implements Replaceable {

    public static final String CHALLENGER = "challenger";
    public static final String SETTINGS = "settings";
    public static final String SPECTATE = "spectate";
    public static final String EXTRAS = "extras";
    public static final String STATS = "stats";

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
    public String getString(final @NotNull Optional<Player> playerOptional, final @NotNull String input) {
        return placeholders.replace(playerOptional, input);
    }

    @Override
    protected String filePath() {
        return Constants.ITEM_NAME_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(CHALLENGER, "&8» &eChallenge player"));
        list.add(new Pair<>(SETTINGS, "&8» &eSettings"));
        list.add(new Pair<>(SPECTATE, "&8» &eSpectate"));
        list.add(new Pair<>(EXTRAS, "&8» &eExtras"));
        list.add(new Pair<>(STATS, "&8» &eStats"));
    }

}
