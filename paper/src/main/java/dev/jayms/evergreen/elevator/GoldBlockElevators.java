package dev.jayms.evergreen.elevator;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import dev.jayms.evergreen.Evergreen;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import vg.civcraft.mc.civmodcore.inventory.items.ItemUtils;
import vg.civcraft.mc.civmodcore.players.settings.PlayerSettingAPI;
import vg.civcraft.mc.civmodcore.players.settings.gui.MenuSection;
import vg.civcraft.mc.civmodcore.players.settings.impl.BooleanSetting;
import vg.civcraft.mc.civmodcore.utilities.DoubleInteractFixer;

public class GoldBlockElevators {

    private Material elevatorBlock = Material.GOLD_BLOCK;

    private BooleanSetting useJumpSneakTP;
    private DoubleInteractFixer interactFixer;
    private String blockName;

    public GoldBlockElevators() {
        this.interactFixer = new DoubleInteractFixer(Evergreen.getInstance());
        this.blockName = ItemUtils.getItemName(this.elevatorBlock).toLowerCase();

        if (this.blockName.startsWith("block of"))
            this.blockName = this.blockName.substring("block of ".length());

        initSettings();
    }

    private void initSettings() {
        MenuSection mainMenu = Evergreen.getInstance().getEvergreenSettingManager().getMainMenu();
        String name = String.format("Use Jump or Sneak to teleport on %s blocks", this.blockName);
        String description = String.format("When true, jumping or sneaking on %s blocks will teleport you up/down, when false, right or left clicking will do the same.",
                this.blockName);
        this.useJumpSneakTP = new BooleanSetting(Evergreen.getInstance(), true, name, "jumpOrSneakTp", description);
        PlayerSettingAPI.registerSetting(this.useJumpSneakTP, mainMenu);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void goldBlockSneak(PlayerToggleSneakEvent event) {
        if (!this.useJumpSneakTP.getValue(event.getPlayer())) {
            return;
        }
        Block below = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (below.getType() != elevatorBlock) {
            return;
        }
        if (!event.isSneaking()) {
            return;
        }
        for (int y = (below.getY() - 1); y > below.getWorld().getMinHeight(); y--) {
            if (doTeleport(below, event.getPlayer(), y)) {
                return;
            }
        }

        String message = String.format("No %s block to teleport you down to. Jump to teleport up instead.", this.blockName);

        event.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void goldBlockJump(PlayerJumpEvent event) {
        if (!this.useJumpSneakTP.getValue(event.getPlayer())) {
            return;
        }
        Block below = event.getFrom().getBlock().getRelative(BlockFace.DOWN);
        if (below.getType() != elevatorBlock) {
            return;
        }
        for (int y = below.getY() + 1; y <= below.getWorld().getMaxHeight(); y++) {
            if (doTeleport(below, event.getPlayer(), y)) {
                return;
            }
        }

        String message = String.format("No %s block to teleport you up to. Sneak to teleport down instead.", this.blockName);

        event.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void goldBlockInteract(PlayerInteractEvent event) {
        if (this.useJumpSneakTP.getValue(event.getPlayer())) {
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (interactFixer.checkInteracted(event.getPlayer(), event.getClickedBlock())) {
                return;
            }
        } else if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        Block below = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (below.getType() != elevatorBlock) {
            return;
        }
        if (event.getClickedBlock() == null) {
            return;
        }
        if (!event.getClickedBlock().equals(below)) {
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            for (int y = below.getY() - 1; y > below.getWorld().getMinHeight(); y--) {
                if (doTeleport(below, event.getPlayer(), y)) {
                    return;
                }
            }

            String message = String.format("No %s block to teleport you down to. Right click to teleport up instead.", this.blockName);

            event.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
        } else {
            for (int y = below.getY() + 1; y <= below.getWorld().getMaxHeight(); y++) {
                if (doTeleport(below, event.getPlayer(), y)) {
                    return;
                }
            }

            String message = String.format("No %s block to teleport you up to. Left click to teleport down instead.", this.blockName);

            event.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
        }
    }

    private boolean doTeleport(Block source, Player player, int y) {
        Block target = source.getWorld().getBlockAt(source.getX(), y, source.getZ());
        if (target.getType() != elevatorBlock) {
            return false;
        }
        if (!TeleportUtil.checkForTeleportSpace(target.getRelative(BlockFace.UP).getLocation())) {
            return false;
        }
        source.getWorld().playSound(source.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 8.0F);
        Location adjustedLocation = target.getLocation().clone();
        adjustedLocation.add(0.5, 1.02, 0.5);
        adjustedLocation.setYaw(player.getLocation().getYaw());
        adjustedLocation.setPitch(player.getLocation().getPitch());
        player.playSound(adjustedLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 8.0F);
        player.teleport(adjustedLocation);
        return true;
    }
}
