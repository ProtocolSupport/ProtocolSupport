package protocolsupport.protocol.packet.mcpe.utils;

import java.util.HashMap;

import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;

import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.NBTTagCompound;

public class InventoryUtils {

	//TODO: other types
	public static int getType(String id) {
		switch (id) {
			case "minecraft:chest":
			case "minecraft:container": {
				return 0;
			}
			case "minecraft:anvil": {
				return 6;
			}
		}
		return -1;
	}

	public static int getFakeBlockForInvType(int type) {
		switch (type) {
			case 0: {
				return 54;
			}
			case 6: {
				return 145;
			}
		}
		throw new IllegalArgumentException("Unknown inventory type "+type);
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

	public static String getName(ItemStack itemstack) {
		NBTTagCompound tag = itemstack.getTag();
		if (tag == null) {
			return "";
		}
		if (!tag.hasKeyOfType("display", 10)) {
			return "";
		}
		NBTTagCompound displayTag = tag.getCompound("display");
		return displayTag.getString("Name");
	}

}
