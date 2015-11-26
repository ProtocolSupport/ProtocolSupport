package protocolsupport.protocol.transformer.mcpe.utils;

import java.util.HashMap;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ItemStack;

public class InventoryUtils {

	//TODO: other types
	public static int getType(String id) {
		switch (id) {
			case "minecraft:chest":
			case "minecraft:container": {
				return 0;
			}
		}
		return -1;
	}

	public static void add(EntityPlayer player, ItemStack itemstack, boolean toPlayer) {
		if (itemstack == null) {
			return;
		}
		Inventory inv = toPlayer ? player.getBukkitEntity().getInventory() : player.getBukkitEntity().getOpenInventory().getTopInventory();
		HashMap<Integer, org.bukkit.inventory.ItemStack> left = inv.addItem(CraftItemStack.asCraftMirror(itemstack));
		for (org.bukkit.inventory.ItemStack bitemstack : left.values()) {
			player.getBukkitEntity().getWorld().dropItem(player.getBukkitEntity().getLocation(), bitemstack);
		}
	}

	public static ItemStack takeAmount(ItemStack itemstack, int amount) {
		ItemStack clone = itemstack.cloneItemStack();
		itemstack.count -= amount;
		clone.count = amount;
		return clone;
	}

}
