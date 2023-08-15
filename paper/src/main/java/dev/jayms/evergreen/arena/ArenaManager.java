package dev.jayms.evergreen.arena;

import dev.jayms.evergreen.region.Region;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ArenaManager {

    private static Map<String, Arena> arenas = new HashMap<>();

    public static void createArena(Arena arena) {
        arenas.put(arena.getName(), arena);
    }

    public static void deleteArena(String name) {
        arenas.remove(name);
    }

    public static Arena getArena(String name) {
        return arenas.get(name);
    }

    public static boolean exists(String name) {
        return getArena(name) != null;
    }

    public static Arena getArena(Location location) {
        for (Arena arena : arenas.values()) {
            if (arena.getRegion().in(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                return arena;
            }
        }
        return null;
    }

    public static Collection<Arena> getArenas() {
        return arenas.values();
    }

    public static boolean overlapsArenas(Region region) {
        Vector[] corners = region.getCorners();
        for (Arena arena : arenas.values()) {
            for (Vector corner : corners) {
                if (arena.getRegion().in(corner.getBlockX(), corner.getBlockY(), corner.getBlockZ())) {
                    return true;
                }
            }
        }
        return false;
    }

}
