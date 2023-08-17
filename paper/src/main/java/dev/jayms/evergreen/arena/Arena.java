package dev.jayms.evergreen.arena;

import dev.jayms.evergreen.region.Region;

import java.util.EnumSet;
import java.util.Set;

public class Arena {

    private int id;
    private String name;
    private Region region;
    private Set<ArenaFlag> flags;
    private ArenaType type;

    public Arena(int id, String name, Region region) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.flags = EnumSet.noneOf(ArenaFlag.class);
        this.type = ArenaType.DEFAULT;
    }

    public Arena(String name, Region region) {
        this(-1, name, region);
    }

    public int getId() {
        return id;
    }

    public void setFlag(ArenaFlag flag, boolean value) {
        if (value) {
            flags.add(flag);
        } else {
            flags.remove(flag);
        }
    }

    public boolean isFlagEnabled(ArenaFlag flag) {
        return flags.contains(flag);
    }

    public Set<ArenaFlag> getFlags() {
        return flags;
    }

    public ArenaType getType() {
        return type;
    }

    public void setType(ArenaType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return region;
    }
}
