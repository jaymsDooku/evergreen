package dev.jayms.evergreen.region;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Region {

    private int x1, y1, z1;
    private int x2, y2, z2;

    public Region() {
    }

    public Region clone() {
        Region clone = new Region();
        clone.x1 = x1;
        clone.y1 = y1;
        clone.z1 = z1;
        clone.x2 = x2;
        clone.y2 = y2;
        clone.z2 = z2;
        return clone;
    }

    public void setOne(int x1, int y1, int z1) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
    }

    public void setTwo(int x2, int y2, int z2) {
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getZ1() {
        return z1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getZ2() {
        return z2;
    }

    public void print(Player player) {
        player.sendMessage(String.format("Min: %d, %d, %d", getMinX(), getMinY(), getMinZ()));
        player.sendMessage(String.format("Max: %d, %d, %d", getMaxX(), getMaxY(), getMaxZ()));
    }

    public int getMinX() {
        return Math.min(x1, x2);
    }

    public int getMinY() {
        return Math.min(y1, y2);
    }

    public int getMinZ() {
        return Math.min(z1, z2);
    }

    public int getMaxX() {
        return Math.max(x1, x2);
    }

    public int getMaxY() {
        return Math.max(y1, y2);
    }

    public int getMaxZ() {
        return Math.max(z1, z2);
    }

    public boolean in(int x, int y, int z) {
        return (x >= getMinX() && y >= getMinY() && z >= getMinZ()) && (x <= getMaxX() && y <= getMaxY() && z <= getMaxZ());
    }

    public Vector[] getCorners() {
        return new Vector[] {
            new Vector(getMinX(), getMinY(), getMinZ()),
            new Vector(getMinX(), getMinY(), getMaxZ()),
            new Vector(getMinX(), getMaxY(), getMinZ()),
            new Vector(getMinX(), getMaxY(), getMaxZ()),
            new Vector(getMaxX(), getMinY(), getMinZ()),
            new Vector(getMaxX(), getMinY(), getMaxZ()),
            new Vector(getMaxX(), getMaxY(), getMinZ()),
            new Vector(getMaxX(), getMaxY(), getMaxZ())
        };
    }
}
