package dev.jayms.evergreen.team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import vg.civcraft.mc.namelayer.GroupManager;
import vg.civcraft.mc.namelayer.NameAPI;
import vg.civcraft.mc.namelayer.NameLayerPlugin;
import vg.civcraft.mc.namelayer.group.Group;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Team {

    private String name;
    private int groupId;
    private Set<UUID> members;

    public Team(String name) {
        this.name = name;
        Group group = GroupManager.getGroup(name);
        if (group == null) {
            group = new Group(name, null, false, null, -1, System.currentTimeMillis());
            this.groupId = NameAPI.getGroupManager().createGroup(group, false);
        } else {
            this.groupId = group.getGroupId();
        }
        this.members = new HashSet<>();
    }

    public Group getGroup() {
        return GroupManager.getGroup(this.groupId);
    }

    public void clear() {
        for (UUID uuid : members) {
            remove(uuid);
        }
        members = new HashSet<>();
    }

    public void add(Player player) {
        add(player.getUniqueId());
    }

    public void add(UUID uuid) {
        getGroup().addMember(uuid, GroupManager.PlayerType.MEMBERS);
        members.add(uuid);
    }

    public boolean in(Player player) {
        return members.contains(player.getUniqueId());
    }

    public void remove(Player player) {
        remove(player.getUniqueId());
    }

    public void remove(UUID uuid) {
        getGroup().removeMember(uuid);
        members.remove(uuid);
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public List<Player> getPlayers() {
        return members.stream().map(Bukkit::getPlayer).collect(Collectors.toList());
    }

}
