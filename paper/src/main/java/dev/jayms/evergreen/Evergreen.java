package dev.jayms.evergreen;

import co.aikar.commands.BukkitCommandManager;
import dev.jayms.evergreen.arena.ArenaListener;
import dev.jayms.evergreen.armor.ArmorListener;
import dev.jayms.evergreen.command.ArenaCommand;
import dev.jayms.evergreen.command.BastionizeCommand;
import dev.jayms.evergreen.command.JoinCommand;
import dev.jayms.evergreen.kit.KitListener;
import dev.jayms.evergreen.lobby.LobbyListener;
import dev.jayms.evergreen.region.RegionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Evergreen extends JavaPlugin {

    private static Evergreen instance;

    public static Evergreen getInstance() {
        return instance;
    }

    private EvergreenSettingManager evergreenSettingManager;

    public EvergreenSettingManager getEvergreenSettingManager() {
        return evergreenSettingManager;
    }

    @Override
    public void onEnable() {
        instance = this;

        evergreenSettingManager = new EvergreenSettingManager();

        getLogger().info("Evergreen is starting!");

        BukkitCommandManager commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new ArenaCommand());
        commandManager.registerCommand(new BastionizeCommand());
        commandManager.registerCommand(new JoinCommand());

        Bukkit.getPluginManager().registerEvents(new RegionListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorListener(new ArrayList<>()), this);
        Bukkit.getPluginManager().registerEvents(new LobbyListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArenaListener(), this);
        Bukkit.getPluginManager().registerEvents(new KitListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Evergreen is shutting down!");
    }

}
