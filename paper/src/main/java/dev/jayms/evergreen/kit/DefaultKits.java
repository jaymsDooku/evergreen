package dev.jayms.evergreen.kit;

import com.github.maxopoly.finale.misc.WeaponModifier;
import dev.jayms.evergreen.elytra.AntiAirMissile;
import dev.jayms.evergreen.utils.MaterialUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class DefaultKits {

    public static ItemStack health() {
        return new ItemStackBuilder(Material.SPLASH_POTION, 1)
                .meta(new ItemMetaBuilder()
                        .potionData(new PotionData(PotionType.INSTANT_HEAL, false, true))).build();
    }

    public static ItemStack fres(int duration) {
        return new ItemStackBuilder(Material.SPLASH_POTION, 1)
                .meta(new ItemMetaBuilder()
                        .potionData(new PotionData(PotionType.FIRE_RESISTANCE, true, false))).build();
    }

    public static ItemStack strength(int duration) {
        return new ItemStackBuilder(Material.SPLASH_POTION, 1)
                .meta(new ItemMetaBuilder()
                        .name(ChatColor.DARK_PURPLE + "Strength Potion")
                        .colour(Color.fromRGB(98, 24, 23))
                        .effects(Arrays.asList(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration * 20, 1)))).build();
    }

    public static ItemStack speed(int duration) {
        return new ItemStackBuilder(Material.SPLASH_POTION, 1)
                .meta(new ItemMetaBuilder()
                        .name(ChatColor.AQUA + "Speed Potion")
                        .colour(Color.fromRGB(122, 172, 195))
                        .effects(Arrays.asList(new PotionEffect(PotionEffectType.SPEED, duration * 20, 1)))).build();
    }

    public static ItemStack regen(int duration) {
        return new ItemStackBuilder(Material.SPLASH_POTION, 1)
                .meta(new ItemMetaBuilder()
                        .name(ChatColor.LIGHT_PURPLE + "Regeneration Potion")
                        .colour(Color.fromRGB(158, 71, 132))
                        .effects(Arrays.asList(new PotionEffect(PotionEffectType.REGENERATION, duration * 20, 0)))).build();
    }

    public static ItemStack poisonExtended(int duration) {
        return new ItemStackBuilder(Material.SPLASH_POTION, 1)
                .meta(new ItemMetaBuilder()
                        .name(ChatColor.DARK_GREEN + "Poison Potion")
                        .colour(Color.fromRGB(43, 135, 63))
                        .effects(Arrays.asList(new PotionEffect(PotionEffectType.POISON, duration * 20, 0)))).build();
    }

    public static ItemStack slow(int duration) {
        return new ItemStackBuilder(Material.SPLASH_POTION, 1)
                .meta(new ItemMetaBuilder()
                        .name(ChatColor.DARK_GRAY + "Slowness Potion")
                        .colour(Color.fromRGB(73, 76, 74))
                        .effects(Arrays.asList(new PotionEffect(PotionEffectType.SLOW, duration * 20, 0)))).build();
    }

    public static ItemStack helmet(Material type) {
        ItemStackBuilder helmIt = new ItemStackBuilder(type, 1);
        if (MaterialUtils.isHelmet(type)) {
            helmIt.meta(new ItemMetaBuilder()
                    .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false)
                    .enchant(Enchantment.DURABILITY, 3, false)
                    .enchant(Enchantment.OXYGEN, 3, false)
                    .enchant(Enchantment.WATER_WORKER, 1, false));
        }
        return WeaponModifier.modifyWeapon(helmIt.build());
    }

    public static ItemStack boots(Material type) {
        ItemStackBuilder bootsIt = new ItemStackBuilder(type, 1);
        if (MaterialUtils.isBoots(type)) {
            bootsIt.meta(new ItemMetaBuilder()
                    .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false)
                    .enchant(Enchantment.DURABILITY, 3, false)
                    .enchant(Enchantment.PROTECTION_FALL, 4, false)
                    .enchant(Enchantment.DEPTH_STRIDER, 3, false));
        }
        return WeaponModifier.modifyWeapon(bootsIt.build());
    }

    public static ItemStack chest(Material type) {
        ItemStackBuilder chestIt = new ItemStackBuilder(type, 1);
        if (MaterialUtils.isChestplate(type)) {
            chestIt.meta(new ItemMetaBuilder()
                    .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false)
                    .enchant(Enchantment.DURABILITY, 3, false));
        }
        return WeaponModifier.modifyWeapon(chestIt.build());
    }

    public static ItemStack legs(Material type) {
        ItemStackBuilder legsIt = new ItemStackBuilder(type, 1);
        if (MaterialUtils.isLeggings(type)) {
            legsIt.meta(new ItemMetaBuilder()
                    .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false)
                    .enchant(Enchantment.DURABILITY, 3, false));
        }
        return WeaponModifier.modifyWeapon(legsIt.build());
    }

    public static ItemStack dhelmet() {
        return helmet(Material.DIAMOND_HELMET);
    }

    public static ItemStack dchest() {
        return chest(Material.DIAMOND_CHESTPLATE);
    }

    public static ItemStack dlegs() {
        return legs(Material.DIAMOND_LEGGINGS);
    }

    public static ItemStack dboots() {
        return boots(Material.DIAMOND_BOOTS);
    }

    public static ItemStack nhelmet() {
        return helmet(Material.NETHERITE_HELMET);
    }

    public static ItemStack nchest() {
        return chest(Material.NETHERITE_CHESTPLATE);
    }

    public static ItemStack nlegs() {
        return legs(Material.NETHERITE_LEGGINGS);
    }

    public static ItemStack nboots() {
        return boots(Material.NETHERITE_BOOTS);
    }

    public static ItemStack swordkb2() {
        ItemStack swordIt = new ItemStackBuilder(Material.DIAMOND_SWORD, 1)
                .meta(new ItemMetaBuilder()
                        .enchant(Enchantment.DAMAGE_ALL, 5, false)
                        .enchant(Enchantment.DURABILITY, 3, false)
                        .enchant(Enchantment.FIRE_ASPECT, 2, false)
                        .enchant(Enchantment.KNOCKBACK, 2, false))
                .build();

        return WeaponModifier.modifyWeapon(swordIt);
    }

    public static ItemStack swordkb1() {
        ItemStack swordIt = new ItemStackBuilder(Material.DIAMOND_SWORD, 1)
                .meta(new ItemMetaBuilder()
                        .enchant(Enchantment.DAMAGE_ALL, 5, false)
                        .enchant(Enchantment.DURABILITY, 3, false)
                        .enchant(Enchantment.FIRE_ASPECT, 2, false)
                        .enchant(Enchantment.KNOCKBACK, 1, false))
                .build();

        return WeaponModifier.modifyWeapon(swordIt);
    }

    public static ItemStack sword() {
        return sword(5);
    }

    public static ItemStack sword(int sharpness) {
        ItemStack swordIt = new ItemStackBuilder(Material.NETHERITE_SWORD, 1)
                .meta(new ItemMetaBuilder()
                        .enchant(Enchantment.DAMAGE_ALL, sharpness, false)
                        .enchant(Enchantment.DURABILITY, 3, false)
                        .enchant(Enchantment.FIRE_ASPECT, 2, false))
                .build();

        return WeaponModifier.modifyWeapon(swordIt);
    }

    public static ItemStack bow() {
        ItemStack bowIt = new ItemStackBuilder(Material.BOW, 1)
                .meta(new ItemMetaBuilder()
                        .enchant(Enchantment.ARROW_DAMAGE, 5, false)
                        .enchant(Enchantment.ARROW_INFINITE, 1, false)
                        .enchant(Enchantment.ARROW_FIRE, 1, false)
                        .enchant(Enchantment.DURABILITY, 3, false)
                )
                .build();
        return bowIt;
    }

    public static ItemStack bowPunch() {
        ItemStack bowIt = new ItemStackBuilder(Material.BOW, 1)
                .meta(new ItemMetaBuilder()
                        .enchant(Enchantment.ARROW_DAMAGE, 5, false)
                        .enchant(Enchantment.ARROW_INFINITE, 1, false)
                        .enchant(Enchantment.ARROW_FIRE, 1, false)
                        .enchant(Enchantment.ARROW_KNOCKBACK, 2, false)
                        .enchant(Enchantment.DURABILITY, 3, false)
                )
                .build();
        return bowIt;
    }

    public static ItemStack crossbow() {
        ItemStack crossbowIt = new ItemStackBuilder(Material.CROSSBOW, 1)
                .meta(new ItemMetaBuilder()
                        .enchant(Enchantment.PIERCING, 4, false)
                        .enchant(Enchantment.QUICK_CHARGE, 3, false)
                        .enchant(Enchantment.DURABILITY, 3, false)
                )
                .build();
        return crossbowIt;
    }

    public static ItemStack antiAirMissiles() {
        ItemStack aaIt = AntiAirMissile.getAntiAirMissile().getItemStack();
        aaIt.setAmount(64);
        return aaIt;
    }

    public static ItemStack elytra() {
        ItemStack elytra = new ItemStackBuilder(Material.ELYTRA, 1)
                .meta(new ItemMetaBuilder()
                        .enchant(Enchantment.DURABILITY, 3, false))
                .build();

        return elytra;
    }

    public static ItemStack carrots() {
        return new ItemStack(Material.GOLDEN_CARROT, 64);
    }

    public static ItemStack pearls() {
        return new ItemStack(Material.ENDER_PEARL, 16);
    }


    public static Kit prot4Set() {
        return new Kit().helmet(nhelmet())
                .chestplate(nchest())
                .leggings(nlegs())
                .boots(nboots())
                .set(0, sword())
                .set(1, pearls())
                .offhand(carrots());
    }

    public static Kit noDebuff() {
        Kit result = prot4Set()
                .range(2, 36, health())
                .set(5, fres(8 * 60))
                .set(6, regen(150))
                .set(7, strength(150))
                .set(8, speed(150))

                .set(24, regen(150))
                .set(25, strength(150))
                .set(26, speed(150))

                .set(33, regen(150))
                .set(34, strength(150))
                .set(35, speed(150));

        return result;
    }

    public static Kit elytraKit() {
        Kit result = prot4Set()
                .range(2, 36, health())
                .set(5, fres(8 * 60))
                .set(6, regen(150))
                .set(7, strength(150))
                .set(8, speed(150))

                .set(9, elytra())
                .set(18, crossbow())
                .set(27, antiAirMissiles())

                .set(24, regen(150))
                .set(25, strength(150))
                .set(26, speed(150))

                .set(33, regen(150))
                .set(34, strength(150))
                .set(35, speed(150));

        return result;
    }
}
