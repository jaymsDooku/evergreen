package dev.jayms.evergreen.region;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RegionListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getPlayer().hasPermission("region.select")) {
            return;
        }

        if (event.getItem() == null) {
            return;
        }

        if (event.getItem().getType() != Material.WOODEN_SHOVEL) {
            return;
        }

        event.setCancelled(true);
        Region cb = Clipboard.getClipboard(event.getPlayer());
        Block block = event.getClickedBlock();
        if (block != null) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                cb.setOne(block.getX(), block.getY(), block.getZ());
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                cb.setTwo(block.getX(), block.getY(), block.getZ());
            }
            cb.print(event.getPlayer());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Clipboard.removeClipboard(event.getPlayer());
    }

}
