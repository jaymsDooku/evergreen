package dev.jayms.evergreen;

import co.aikar.commands.BukkitCommandManager;
import dev.jayms.evergreen.arena.Arena;
import dev.jayms.evergreen.arena.ArenaListener;
import dev.jayms.evergreen.arena.ArenaManager;
import dev.jayms.evergreen.armor.ArmorListener;
import dev.jayms.evergreen.command.ArenaCommand;
import dev.jayms.evergreen.command.BastionizeCommand;
import dev.jayms.evergreen.command.EGiveCommand;
import dev.jayms.evergreen.command.JoinCommand;
import dev.jayms.evergreen.elytra.AntiAirMissile;
import dev.jayms.evergreen.elytra.CrossbowListener;
import dev.jayms.evergreen.elytra.ElytraListener;
import dev.jayms.evergreen.kit.KitListener;
import dev.jayms.evergreen.lobby.LobbyListener;
import dev.jayms.evergreen.region.RegionListener;
import dev.jayms.evergreen.server.ServerListener;
import dev.jayms.evergreen.storage.ArenaDAO;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vg.civcraft.mc.civmodcore.ACivMod;
import vg.civcraft.mc.civmodcore.dao.ManagedDatasource;

import java.util.ArrayList;

public class Evergreen extends ACivMod {

    private static Evergreen instance;

    public static Evergreen getInstance() {
        return instance;
    }

    private EvergreenSettingManager evergreenSettingManager;

    public EvergreenSettingManager getEvergreenSettingManager() {
        return evergreenSettingManager;
    }

    private ArenaDAO arenaDAO;

    @Override
    public void onEnable() {
        instance = this;

        EvergreenConfig.loadConfig(this);

        evergreenSettingManager = new EvergreenSettingManager();

        getLogger().info("Evergreen is starting!");

        BukkitCommandManager commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new ArenaCommand());
        commandManager.registerCommand(new BastionizeCommand());
        commandManager.registerCommand(new JoinCommand());
        commandManager.registerCommand(new EGiveCommand());

        Bukkit.getPluginManager().registerEvents(new RegionListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorListener(new ArrayList<>()), this);
        Bukkit.getPluginManager().registerEvents(new LobbyListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArenaListener(), this);
        Bukkit.getPluginManager().registerEvents(new KitListener(), this);
        Bukkit.getPluginManager().registerEvents(new ElytraListener(), this);
        Bukkit.getPluginManager().registerEvents(new CrossbowListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);

        AntiAirMissile.startInstanceRunner();

        ManagedDatasource managedDatasource = ManagedDatasource.construct(this, EvergreenConfig.getDatabaseCredentials());
        arenaDAO = new ArenaDAO(managedDatasource);
        if (!managedDatasource.updateDatabase()) {
            warning("Failed to update database, stopping Evergreen.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        ArenaManager.setArenas(arenaDAO.loadArenas());
    }

    @Override
    public void onDisable() {
        getLogger().info("Evergreen is shutting down!");

        arenaDAO.save(ArenaManager.getArenas());
    }

}
