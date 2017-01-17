package protocolsupport.zplatform.itemstack;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import protocolsupport.zplatform.ServerImplementationType;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotItemStackWrapper;

public abstract class ItemStackWrapper {

	public static ItemStackWrapper createNull() {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return SpigotItemStackWrapper.createNull();
			}
			default: {
				//TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static ItemStackWrapper create(Material material) {
		return ItemStackWrapper.create(material.getId());
	}

	public static ItemStackWrapper create(int typeId) {
		switch (ServerImplementationType.get()) {
			case SPIGOT: {
				return SpigotItemStackWrapper.create(typeId);
			}
			default: {
				//TODO: implement for glowstone
				throw new NotImplementedException("Not implemented yet");
			}
		}
	}

	public abstract ItemStack asBukkitMirror();

	public abstract boolean isNull();

	public abstract int getTypeId();

	@SuppressWarnings("deprecation")
	public Material getType() {
		return Material.getMaterial(getTypeId());
	}

	public abstract void setTypeId(int typeId);

	@SuppressWarnings("deprecation")
	public void setType(Material material) {
		setTypeId(material.getId());
	}

	public abstract int getData();

	public abstract void setData(int data);

	public abstract int getAmount();

	public abstract void setAmount(int amount);

	public abstract String getDisplayName();

	public abstract void setDisplayName(String displayName);

	public abstract NBTTagCompoundWrapper getTag();

	public abstract void setTag(NBTTagCompoundWrapper tag);

	public abstract ItemStackWrapper cloneItemStack();

}
