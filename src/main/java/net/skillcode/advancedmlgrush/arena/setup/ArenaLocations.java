package net.skillcode.advancedmlgrush.arena.setup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.skillcode.advancedmlgrush.arena.ArenaType;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Location;

import java.util.List;

@Getter
@AllArgsConstructor
public class ArenaLocations {

    private final String templateName;

    private final int firstCorner;
    private final int secondCorner;

    private final double maxBuildHeight;
    private final double deathHeight;

    private final List<Location> spawnLocations;
    private final List<Pair<Location, Location>> bedLocations;

}
