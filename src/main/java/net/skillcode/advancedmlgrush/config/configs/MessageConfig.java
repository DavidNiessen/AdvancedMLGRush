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
public class MessageConfig extends Configurable implements Replaceable {

    public static final String PREFIX = "prefix";
    public static final String BUILD_MODE_ON = "build_mode_on";
    public static final String BUILD_MODE_OFF = "build_mode_off";
    public static final String QUEUE_JOIN = "queue_join";
    public static final String QUEUE_LEAVE = "queue_leave";

    private final Placeholders placeholders;

    @Inject
    public MessageConfig(final Placeholders placeholders) {
        this.placeholders = placeholders;
    }

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    public String getString(final @NotNull Optional<Player> optional, final @NotNull String path) {
        return placeholders.replace(optional, super.getString(path));
    }

    public String getWithPrefix(final @NotNull Optional<Player> optionalPlayer, final @NotNull String path) {
        return getString(optionalPlayer, PREFIX) + getString(optionalPlayer, path);
    }

    @Override
    protected String filePath() {
        return Constants.MESSAGE_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(PREFIX, "&8» &6&lAdvancedMLGRush &8| &7"));
        list.add(new Pair<>(BUILD_MODE_ON, "&aYou can build now."));
        list.add(new Pair<>(BUILD_MODE_OFF, "&cYou can no longer build now."));
        list.add(new Pair<>(QUEUE_JOIN, "&aYou entered the queue."));
        list.add(new Pair<>(QUEUE_LEAVE, "&aYou left the queue."));
    }

}
