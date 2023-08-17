package dev.jayms.evergreen.utils;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    public static net.minecraft.world.item.ItemStack getNMSStack(ItemStack is) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        CompoundTag compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new CompoundTag();
        nmsStack.setTag(compound);
        return nmsStack;
    }

    public static ItemStack setAAKey(ItemStack is, String key) {
        net.minecraft.world.item.ItemStack nmsItem = ItemUtil.getNMSStack(is);
        CompoundTag compoundTag = nmsItem.getTag();
        compoundTag.putString("aa-key", key);
        nmsItem.setTag(compoundTag);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }
    public static String getAAKey(ItemStack is) {
        net.minecraft.world.item.ItemStack nmsItem = ItemUtil.getNMSStack(is);
        CompoundTag compoundTag = nmsItem.getTag();
        return compoundTag.getString("aa-key");
    }


}
