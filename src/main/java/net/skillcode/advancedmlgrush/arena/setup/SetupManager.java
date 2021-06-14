package net.skillcode.advancedmlgrush.arena.setup;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.arena.ArenaType;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class SetupManager implements Registrable, EventHandler {

    private final Map<Player, TemplateSetup> setupMap = new ConcurrentHashMap<>();

    @Inject
    private SetupFactory setupFactory;

    public void startSetup(final @NotNull Player player, final @NotNull ArenaType arenaType) {
        final TemplateSetup templateSetup = setupFactory.create(player, arenaType, () -> setupMap.remove(player));
        templateSetup.startSetup();
        setupMap.put(player, templateSetup);
    }

    @Override
    public void unregister(final @NotNull Player player) {
        setupMap.remove(player);
    }

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(new EventListener<AsyncPlayerChatEvent>(AsyncPlayerChatEvent.class) {
            @Override
            protected void onEvent(final @NotNull AsyncPlayerChatEvent event) {
                final Player player = event.getPlayer();
                if (setupMap.containsKey(player)
                        && event.getMessage().equalsIgnoreCase("next")) {
                    setupMap.get(player).nextStep();
                    event.setCancelled(true);
                } else if (event.getMessage().equals("hi")) {
                    startSetup(player, ArenaType.ONE_X_ONE);
                }
            }
        });
    }
}
