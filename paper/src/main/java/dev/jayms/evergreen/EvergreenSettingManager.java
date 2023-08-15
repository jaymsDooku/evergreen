package dev.jayms.evergreen;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.players.settings.PlayerSettingAPI;
import vg.civcraft.mc.civmodcore.players.settings.gui.MenuSection;

public class EvergreenSettingManager {

    private MenuSection mainMenu;

    public EvergreenSettingManager() {
        this.mainMenu = PlayerSettingAPI.getMainMenu().createMenuSection("Evergreen", "Config values for Evergreen", new ItemStack(
                Material.GRASS_BLOCK));
    }

    /**
     * Returns the Menu Section used to bundle together all SAH /config tweaks
     * @return SAH /config Menu
     */
    public MenuSection getMainMenu() {
        return mainMenu;
    }

}
