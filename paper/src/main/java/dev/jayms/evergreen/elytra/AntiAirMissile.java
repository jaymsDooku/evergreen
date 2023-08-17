package dev.jayms.evergreen.elytra;

import dev.jayms.evergreen.Evergreen;
import dev.jayms.evergreen.utils.ItemUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AntiAirMissile {

    private String key;

    private String itemName;
    private Color itemColor;

    private double power;
    private double speed;
    private double damage;
    private double damageRadius;
    private double homingRadius;
    private double homingStrength;
    private double activateRadius;
    private double maxRange;
    private double gravity;

    public AntiAirMissile(String key, String itemName, Color itemColor, double power, double speed, double damage, double damageRadius,
                          double homingRadius, double homingStrength, double activateRadius, double maxRange, double gravity) {
        this.key = key;
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.power = power;
        this.speed = speed;
        this.damage = damage;
        this.damageRadius = damageRadius;
        this.homingRadius = homingRadius;
        this.homingStrength = homingStrength;
        this.activateRadius = activateRadius;
        this.maxRange = maxRange;
        this.gravity = gravity;
    }

    public double getPower() {
        return power;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDamage() {
        return damage;
    }

    public double getDamageRadius() {
        return damageRadius;
    }

    public double getHomingRadius() {
        return homingRadius;
    }

    public double getHomingStrength() {
        return homingStrength;
    }

    public double getActivateRadius() {
        return activateRadius;
    }

    public double getMaxRange() {
        return maxRange;
    }

    public double getGravity() {
        return gravity;
    }

    public ItemStack getItemStack() {
        ItemStack result = new ItemStack(Material.TIPPED_ARROW, 1);

        PotionMeta meta = (PotionMeta) result.getItemMeta();
        meta.displayName(Component.text(itemName));
        meta.setColor(itemColor);
        result.setItemMeta(meta);

        result = ItemUtil.setAAKey(result, key);

        return result;
    }

    private static List<AntiAirMissileInstance> instances = new ArrayList<>();

    public void fireAAMissile(Player shooter) {
        AntiAirMissileInstance antiAirMissileInstance = new AntiAirMissileInstance(this, shooter);
        instances.add(antiAirMissileInstance);
    }

    public static void progressInstances() {
        Iterator<AntiAirMissileInstance> instancesIt = instances.iterator();
        while (instancesIt.hasNext()) {
            AntiAirMissileInstance instance = instancesIt.next();
            if (instance.progress()) {
                instancesIt.remove();
            }
        }
    }

    private static final AntiAirMissile AA = new AntiAirMissile(
            "aa",
            ChatColor.RED + "Anti Air Missile",
            Color.RED,
            1,
            1,
            10,
            1,
            5,
            1,
            1,
            100,
            0.01
    );

    public static AntiAirMissile getAntiAirMissile() {
        return AA;
    }

    public static AntiAirMissile getAntiAirMissile(ItemStack itemStack) {
        String key = ItemUtil.getAAKey(itemStack);
        if (key.equals("aa")) {
            return AA;
        }
        return null;
    }

    public static void startInstanceRunner() {
        Bukkit.getScheduler().runTaskTimer(Evergreen.getInstance(), AntiAirMissile::progressInstances, 0L, 1L);
    }

}
