package protocolsupport.zplatform.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class ItemStackWrapper {

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

	public abstract void setDisplayName(String displayName);

	public abstract NBTTagCompoundWrapper getTag();

	public abstract void setTag(NBTTagCompoundWrapper tag);

	public abstract ItemStackWrapper cloneItemStack();

}
