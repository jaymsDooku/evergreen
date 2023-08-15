package dev.jayms.evergreen.lobby;

import dev.jayms.evergreen.arena.Arena;
import dev.jayms.evergreen.arena.ArenaManager;
import dev.jayms.evergreen.arena.ArenaType;
import dev.jayms.evergreen.arena.event.ArenaEnterEvent;
import dev.jayms.evergreen.arena.event.ArenaExitEvent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class LobbyListener implements Listener {

    public void enterLobby(Player player) {
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Arena arena = ArenaManager.getArena(event.getPlayer().getLocation());
        if (arena == null) {
            enterLobby(event.getPlayer());
        }
    }

    @EventHandler
    public void onArenaEnter(ArenaEnterEvent event) {
        Arena arena = event.getArena();
        if (arena.getType() == ArenaType.FFA) {
            event.getPlayer().getInventory().addItem(new ItemStack(Material.BOOK, 1));
        }
    }

    @EventHandler
    public void onArenaExit(ArenaExitEvent event) {
        enterLobby(event.getPlayer());
    }

}
