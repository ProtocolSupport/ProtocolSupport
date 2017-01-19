package protocolsupport.zplatform;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_11_R1.Item;
import net.minecraft.server.v1_11_R1.LocaleI18n;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotNBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class MiscPlatformUtils {

	public static String localize(String key, Object... args) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return LocaleI18n.a(key, args);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return CraftItemStack.asCraftMirror(new net.minecraft.server.v1_11_R1.ItemStack(((SpigotNBTTagCompoundWrapper) tag).unwrap()));
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				net.minecraft.server.v1_11_R1.ItemStack nmsitemstack = CraftItemStack.asNMSCopy(itemstack);
				NBTTagCompound compound = new NBTTagCompound();
				nmsitemstack.save(compound);
				return SpigotNBTTagCompoundWrapper.wrap(compound);
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static Integer getItemIdByName(String registryname) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				Item item = Item.b(registryname);
				if (item != null) {
					return Item.getId(item);
				}
				return null;
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static String getOutdatedServerMessage() {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return SpigotConfig.outdatedServerMessage;
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public static boolean isBungeeEnabled() {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return SpigotConfig.bungee;
			}
			default: {
				// TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

}
