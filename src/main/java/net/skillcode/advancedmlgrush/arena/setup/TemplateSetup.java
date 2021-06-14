package net.skillcode.advancedmlgrush.arena.setup;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.arena.ArenaType;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class TemplateSetup {

    private final Player player;
    private final ArenaType arenaType;
    private final Runnable onComplete;

    private final List<Location> spawnLocations = new CopyOnWriteArrayList<>();
    private final List<Pair<Location, Location>> bedLocations = new CopyOnWriteArrayList<>();
    private final List<SetupStep> setupSteps = new ArrayList<>();

    private String templateName;

    private Location firstCorner;
    private Location secondCorner;

    private double maxBuildHeight;
    private double deathHeight;

    private int nextIndex = 0;

    @Inject
    private MessageConfig messageConfig;

    @Inject
    public TemplateSetup(final @Assisted Player player, final @Assisted ArenaType arenaType, final @Assisted Runnable onComplete) {
        this.player = player;
        this.arenaType = arenaType;
        this.onComplete = onComplete;
    }

    @PostConstruct
    public void initSteps() {
        setupSteps.add(player -> {
            firstCorner = player.getLocation();
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_SECOND_CORNER));
        });
        setupSteps.add(player -> {
            secondCorner = player.getLocation();
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_MAX_BUILD_HEIGHT));
        });
        setupSteps.add(player -> {
            maxBuildHeight = player.getLocation().getY();
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_DEATH_HEIGHT));
        });
        setupSteps.add(player -> {
            deathHeight = player.getLocation().getY();
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_SPAWN_PLAYER_1));
        });
        setupSteps.add(player -> {
            spawnLocations.add(player.getLocation());
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_SPAWN_PLAYER_2));
        });
        setupSteps.add(player -> {
            spawnLocations.add(player.getLocation());
            if (arenaType == ArenaType.ONE_X_FOUR) {
                player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_SPAWN_PLAYER_3));
            } else {
                nextStep();
            }
        });
        setupSteps.add(player -> {
            if (arenaType == ArenaType.ONE_X_FOUR) {
                spawnLocations.add(player.getLocation());
                player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_SPAWN_PLAYER_4));
            } else {
                nextStep();
            }
        });
        setupSteps.add(player -> {
            if (arenaType == ArenaType.ONE_X_FOUR) {
                spawnLocations.add(player.getLocation());
            }
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_BED_PLAYER_1));
        });
        setupSteps.add(player -> {
            bedLocations.add(new Pair<>(player.getLocation(), player.getTargetBlock((Set<Material>) null, 5).getLocation()));
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_BED_PLAYER_2));
        });
        setupSteps.add(player -> {
            bedLocations.add(new Pair<>(player.getLocation(), player.getTargetBlock((Set<Material>) null, 5).getLocation()));
            if (arenaType == ArenaType.ONE_X_FOUR) {
                player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_BED_PLAYER_3));
            } else {
                nextStep();
            }
        });
        setupSteps.add(player -> {
            if (arenaType == ArenaType.ONE_X_FOUR) {
                bedLocations.add(new Pair<>(player.getLocation(), player.getTargetBlock((Set<Material>) null, 5).getLocation()));
                player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_BED_PLAYER_4));
            } else {
                nextStep();
            }
        });
        setupSteps.add(player -> {
            if (arenaType == ArenaType.ONE_X_FOUR) {
                bedLocations.add(new Pair<>(player.getLocation(), player.getTargetBlock((Set<Material>) null, 5).getLocation()));
            }
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.SETUP_FINISH));
            onComplete.run();
        });
    }

    public void startSetup() {
        setupSteps.get(0).run(player);
        nextIndex++;
    }

    public void nextStep() {
        if (nextIndex < setupSteps.size()) {
            setupSteps.get(nextIndex).run(player);
            nextIndex++;
        }
    }
}
