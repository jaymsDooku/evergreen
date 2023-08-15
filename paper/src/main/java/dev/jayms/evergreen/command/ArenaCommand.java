package dev.jayms.evergreen.command;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import dev.jayms.evergreen.arena.Arena;
import dev.jayms.evergreen.arena.ArenaFlag;
import dev.jayms.evergreen.arena.ArenaManager;
import dev.jayms.evergreen.arena.ArenaType;
import dev.jayms.evergreen.region.Clipboard;
import dev.jayms.evergreen.region.Region;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("arena")
public class ArenaCommand extends BaseCommand {

    @CommandAlias("create")
    public void create(Player sender, String name) {
        Region region = Clipboard.getClipboard(sender);
//        if (ArenaManager.overlapsArenas(region)) {
//            sender.sendMessage("Overlapping existing arenas.");
//            return;
//        }
        Arena arena = new Arena(name, region.clone());
        ArenaManager.createArena(arena);
        sender.sendMessage("Arena created: " + name);
    }

    @CommandAlias("delete")
    public void delete(Player sender, String name) {
        if (!ArenaManager.exists(name)) {
            sender.sendMessage("Arena does not exist.");
            return;
        }
        ArenaManager.deleteArena(name);
        sender.sendMessage("Arena deleted: " + name);
    }

    @CommandAlias("list")
    public void list(Player sender) {
        for (Arena arena : ArenaManager.getArenas()) {
            sender.sendMessage("-" + arena.getName());
        }
    }

    @CommandAlias("flag")
    public void flag(Player sender, String arenaName, String property, boolean value) {
        Arena arena = ArenaManager.getArena(arenaName);
        if (arena == null) {
            sender.sendMessage("Arena does not exist.");
            return;
        }
        try {
            ArenaFlag flag = ArenaFlag.valueOf(property.toUpperCase());
            arena.setFlag(flag, value);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(property + " is not a valid flag.");
        }
    }

    @CommandAlias("type")
    public void type(Player sender, String arenaName, String type) {
        Arena arena = ArenaManager.getArena(arenaName);
        if (arena == null) {
            sender.sendMessage("Arena does not exist.");
            return;
        }
        try {
            ArenaType typeEnum = ArenaType.valueOf(type.toUpperCase());
            arena.setType(typeEnum);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(type + " is not a valid arena type.");
        }
    }

    @CommandAlias("info")
    public void info(Player sender, String arenaName) {
        Arena arena = ArenaManager.getArena(arenaName);
        if (arena == null) {
            sender.sendMessage("Arena does not exist.");
            return;
        }
        sender.sendMessage("Name: " + arena.getName());
        sender.sendMessage("Region: ");
        arena.getRegion().print(sender);
        sender.sendMessage("Flags: ");
        for (ArenaFlag flag : ArenaFlag.values()) {
            sender.sendMessage("-" + (arena.isFlagEnabled(flag) ? ChatColor.GREEN : ChatColor.RED) +  flag.toString());
        }
    }

}
