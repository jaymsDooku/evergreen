package dev.jayms.evergreen.arena;

import dev.jayms.evergreen.region.Region;

import java.util.EnumSet;
import java.util.Set;

public class Arena {

    private String name;
    private Region region;
    private Set<ArenaFlag> flags;
    private ArenaType type;


    public Arena(String name, Region region) {
        this.name = name;
        this.region = region;
        this.flags = EnumSet.noneOf(ArenaFlag.class);
        this.type = ArenaType.DEFAULT;
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
