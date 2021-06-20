/*
 * Copyright (c) 2021 SkillCode
 *
 * This class is a part of the source code of the
 * AdvancedMLGRush plugin from SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.sound.SoundParser;
import net.skillcode.advancedmlgrush.util.Pair;
import net.skillcode.advancedmlgrush.libs.xseries.XSound;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class SoundConfig extends Configurable {

    public static final String INVENTORY_OPEN = "inventory_open";
    public static final String INVENTORY_CLICK = "inventory_click";
    public static final String INVENTORY_CLICK_LOCKED = "inventory_click_locked";

    private final SoundParser soundParser;

    @Inject
    public SoundConfig(final SoundParser soundParser) {
        this.soundParser = soundParser;
    }

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.SOUND_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(INVENTORY_OPEN, soundParser.parseString(XSound.BLOCK_PISTON_EXTEND, 1, 1.9F)));
        list.add(new Pair<>(INVENTORY_CLICK, soundParser.parseString(XSound.UI_BUTTON_CLICK, 1, 2F)));
        list.add(new Pair<>(INVENTORY_CLICK_LOCKED, soundParser.parseString(XSound.ENTITY_ITEM_BREAK, 1, 0.6F)));
    }
}
