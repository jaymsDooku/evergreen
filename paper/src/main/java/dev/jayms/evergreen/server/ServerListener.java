package dev.jayms.evergreen.server;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Arrays;
import java.util.List;

public class ServerListener implements Listener {

    private static final List<String> MOTD = Arrays.asList(
            "The grass is always greener on the other side!",
            "Is there a God? If not, what are all these churches for? And who is Jesus' dad?",
            "Don't ever, for any reason, do anything, to anyone, for any reason, ever, no matter what, no matter where, or who, or who you are with, or where you are going, or where you've been, ever, for any reason whatsoever."
    );
    private static int motdInd = 0;

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        event.setMotd(ChatColor.DARK_GREEN + "Serenno" + ChatColor.BLACK + "[" + ChatColor.GREEN + ChatColor.ITALIC + "Evergreen" + ChatColor.BLACK + "]" + ChatColor.RESET + MOTD.get(motdInd));
        motdInd++;
        if (motdInd >= MOTD.size()) {
            motdInd = 0;
        }

    }
}
