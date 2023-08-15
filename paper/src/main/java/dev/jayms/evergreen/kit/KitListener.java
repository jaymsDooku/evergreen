package dev.jayms.evergreen.kit;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class KitListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) {
            return;
        }

        if (event.getItem().getType() == Material.BOOK) {
            event.getPlayer().getInventory().removeItem(event.getItem());
            DefaultKits.noDebuff().load(event.getPlayer());
        }
    }

}
