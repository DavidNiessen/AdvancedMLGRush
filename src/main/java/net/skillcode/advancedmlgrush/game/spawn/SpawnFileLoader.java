/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.game.spawn;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.json.utils.JsonUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;

@Singleton
public class SpawnFileLoader {

    private final JavaPlugin javaPlugin;

    @Getter
    private Optional<SpawnFile> spawnFileOptional = Optional.empty();

    @Inject
    public SpawnFileLoader(final @NotNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }


    @PostConstruct
    public void loadFileIfExists() {
        final File file = new File(Constants.SPAWN_FILE_PATH);
        if (file.exists()) {
            spawnFileOptional = Optional.ofNullable(JsonUtils.getFromFile(
                    SpawnFile.class, file));
        }
    }

    public void createSpawnFile(final @NotNull SpawnFile spawnFile) {
        final File file = new File(Constants.SPAWN_FILE_PATH);
        if (file.exists()) {
            if (!file.delete()) {
                javaPlugin.getLogger().warning(Constants.SPAWN_FILE_DELETE_ERROR);
            }
        }

        JsonUtils.toFile(spawnFile, Constants.SPAWN_FILE_PATH, true);
    }
}
