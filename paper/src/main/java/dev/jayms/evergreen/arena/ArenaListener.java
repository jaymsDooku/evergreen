package dev.jayms.evergreen.arena;

import dev.jayms.evergreen.arena.event.ArenaEnterEvent;
import dev.jayms.evergreen.arena.event.ArenaExitEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArenaListener implements Listener {

    private Map<UUID, Arena> insideArena = new HashMap<>();

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (insideArena.containsKey(event.getPlayer().getUniqueId())) {
            return;
        }

        Arena respawn = ArenaManager.getArena(event.getRespawnLocation());
        if (respawn == null || !respawn.getName().equals(insideArena.get(event.getPlayer().getUniqueId()).getName())) {
            insideArena.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        insideArena.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Arena arena = ArenaManager.getArena(player.getLocation());
        if (arena != null && arena.getType() == ArenaType.FFA) {
            event.getDrops().clear();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Arena to = ArenaManager.getArena(event.getTo());

        Player player = event.getPlayer();
        Arena inside = insideArena.get(player.getUniqueId());

        if (inside == null && to != null) {
            ArenaEnterEvent enterEvent = new ArenaEnterEvent(player, to);
            if (enterEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
            Bukkit.getPluginManager().callEvent(enterEvent);

            player.sendActionBar("Entering " + to.getName());
            insideArena.put(player.getUniqueId(), to);
        } else if (inside != null && to != null && !to.getName().equals(inside.getName())) {
            ArenaExitEvent exitEvent = new ArenaExitEvent(player, inside);
            if (exitEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
            Bukkit.getPluginManager().callEvent(exitEvent);

            ArenaEnterEvent enterEvent = new ArenaEnterEvent(player, inside);
            if (enterEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
            Bukkit.getPluginManager().callEvent(enterEvent);

            player.sendActionBar("Exiting " + inside.getName() + " Entering " + to.getName());
            insideArena.put(player.getUniqueId(), to);
        } else if (inside != null && to == null) {
            ArenaExitEvent exitEvent = new ArenaExitEvent(player, inside);
            if (exitEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
            Bukkit.getPluginManager().callEvent(exitEvent);

            player.sendActionBar("Exiting " + inside.getName());
            insideArena.remove(player.getUniqueId());
        }
    }
}
