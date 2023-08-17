package dev.jayms.evergreen.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import dev.jayms.evergreen.elytra.AntiAirMissile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EGiveCommand extends BaseCommand {

    @CommandAlias("egive")
    public void giveAAMissiles(Player sender, int amount) {
        AntiAirMissile antiAirMissile = AntiAirMissile.getAntiAirMissile();
        ItemStack itemStack = antiAirMissile.getItemStack();
        itemStack.setAmount(amount);
        sender.getInventory().addItem(itemStack);
        sender.sendMessage("Given " + amount + " " + itemStack.getItemMeta().displayName());
    }
}
