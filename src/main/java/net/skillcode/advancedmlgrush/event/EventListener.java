package net.skillcode.advancedmlgrush.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public abstract class EventListener<E extends Event> {

    private final Class<? extends Event> eventClass;

    protected abstract void onEvent(final @NotNull E event);

    public void callEvent(final @NotNull Event event) {
        if (event.getClass().equals(eventClass)) {
            onEvent((E) event);
        }
    }


}