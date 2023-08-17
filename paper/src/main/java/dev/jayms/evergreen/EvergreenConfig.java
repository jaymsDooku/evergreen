package dev.jayms.evergreen;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import vg.civcraft.mc.civmodcore.dao.DatabaseCredentials;
import vg.civcraft.mc.civmodcore.dao.ManagedDatasource;
import vg.civcraft.mc.namelayer.NameLayerPlugin;

import java.util.logging.Level;

public class EvergreenConfig {

    private static Location spawn;
    private static DatabaseCredentials databaseCredentials;

    public static void loadConfig(Evergreen evergreen) {
        evergreen.getLogger().info("Parsing Evergreen config...");
        evergreen.saveDefaultConfig();
        evergreen.reloadConfig();

        FileConfiguration config = evergreen.getConfig();
        String spawnWorld = config.getString("spawn.world", "world");
        double x = config.getDouble("spawn.x");
        double y = config.getDouble("spawn.y");
        double z = config.getDouble("spawn.z");
        spawn = new Location(Bukkit.getWorld(spawnWorld), x, y, z);

        databaseCredentials = new DatabaseCredentials(null, null, null, 0, "sqlite", "evergreen", 10,
                10000, 600000, 7200000);
    }

    public static Location getSpawn() {
        return spawn;
    }

    public static DatabaseCredentials getDatabaseCredentials() {
        return databaseCredentials;
    }
}
