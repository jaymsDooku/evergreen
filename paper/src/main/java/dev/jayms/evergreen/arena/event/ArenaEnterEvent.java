package dev.jayms.evergreen.arena.event;

import dev.jayms.evergreen.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ArenaEnterEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private Arena arena;

    public ArenaEnterEvent(Player player, Arena arena) {
        super(player);
        this.arena = arena;
    }

    public Arena getArena() {
        return arena;
    }

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    public final static HandlerList getHandlerList(){
        return handlers;
    }

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    @Override
    public final HandlerList getHandlers(){
        return handlers;
    }

    /**
     * Sets if this event should be cancelled.
     *
     * @param cancel If this event should be cancelled.
     */
    public final void setCancelled(final boolean cancel){
        this.cancel = cancel;
    }

    /**
     * Gets if this event is cancelled.
     *
     * @return If this event is cancelled
     */
    public final boolean isCancelled(){
        return cancel;
    }

}
