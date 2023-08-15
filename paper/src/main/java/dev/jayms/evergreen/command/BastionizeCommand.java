package dev.jayms.evergreen.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import dev.jayms.evergreen.region.Clipboard;
import dev.jayms.evergreen.region.Region;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BastionizeCommand extends BaseCommand {

    @CommandAlias("bastionize")
    @Description("Bastionize area.")
    public void execute(Player sender, String groupName, String targetMaterial, String bastionType) {
        Region region = Clipboard.getClipboard(sender);
        
    }

}
