package dev.jayms.evergreen.elytra;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class CrossbowListener implements Listener {

    @EventHandler
    public void onShootCrossbow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player shooter = (Player) event.getEntity();
        ItemStack bow = event.getBow();
        if (bow.getType() != Material.CROSSBOW) {
            return;
        }

        ItemStack consumable = event.getConsumable();
        if (consumable.getType() == Material.TIPPED_ARROW) {
            AntiAirMissile antiAirMissile = AntiAirMissile.getAntiAirMissile(consumable);
            if (antiAirMissile == null) {
                return;
            }

            event.setCancelled(true);
            antiAirMissile.fireAAMissile(shooter);
        }
    }

}
