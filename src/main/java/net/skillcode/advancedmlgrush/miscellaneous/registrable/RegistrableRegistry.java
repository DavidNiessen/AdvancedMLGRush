package net.skillcode.advancedmlgrush.miscellaneous.registrable;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.game.buildmode.BuildModeManager;

@Singleton
public class RegistrableRegistry {

    @Inject
    private RegistrableManager manager;
    @Inject
    private BuildModeManager buildModeManager;

    @PostConstruct
    public void register() {
        manager.registerRegistrable(buildModeManager);
    }

}
