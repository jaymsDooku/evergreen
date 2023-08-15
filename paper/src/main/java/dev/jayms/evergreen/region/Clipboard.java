package dev.jayms.evergreen.region;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Clipboard {

    private static Map<UUID, Region> clipboards = new HashMap<>();

    public static Region getClipboard(Player player) {
        Region region = clipboards.get(player.getUniqueId());
        if (region == null) {
            region = new Region();
            clipboards.put(player.getUniqueId(), region);
        }
        return region;
    }

    public static void removeClipboard(Player player) {

    }

}
