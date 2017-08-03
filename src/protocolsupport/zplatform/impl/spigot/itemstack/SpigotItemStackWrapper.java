package protocolsupport.zplatform.impl.spigot.itemstack;

import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.ItemStack;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class SpigotItemStackWrapper extends ItemStackWrapper {

	protected final ItemStack itemstack;
	protected SpigotItemStackWrapper(ItemStack itemstack) {
		this.itemstack = itemstack;
	}

	public static ItemStackWrapper createNull() {
		return new SpigotItemStackWrapper(ItemStack.a);
	}

	public static ItemStackWrapper create(int typeId) {
		return new SpigotItemStackWrapper(new ItemStack(Item.getById(typeId)));
	}

	@Override
	public org.bukkit.inventory.ItemStack asBukkitMirror() {
		return CraftItemStack.asCraftMirror(itemstack);
	}

	@Override
	public boolean isNull() {
		return itemstack.isEmpty();
	}

	@Override
	public int getTypeId() {
		return Item.getId(itemstack.getItem());
	}

	@Override
	@SuppressWarnings("deprecation")
	public Material getType() {
		return Material.getMaterial(getTypeId());
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setTypeId(int typeId) {
		itemstack.setItem(Item.getById(typeId));
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setType(Material material) {
		setTypeId(material.getId());
	}

	@Override
	public int getData() {
		return itemstack.getData();
	}

	@Override
	public void setData(int data) {
		itemstack.setData(data);
	}

	@Override
	public int getAmount() {
		return itemstack.getCount();
	}

	@Override
	public void setAmount(int amount) {
		itemstack.setCount(amount);
	}

	@Override
	public void setDisplayName(String displayName) {
		itemstack.g(displayName);
	}

	@Override
	public SpigotNBTTagCompoundWrapper getTag() {
		return new SpigotNBTTagCompoundWrapper(itemstack.getTag());
	}

	@Override
	public void setTag(NBTTagCompoundWrapper tag) {
		if (tag.isNull()) {
			itemstack.setTag(null);
		} else {
			itemstack.setTag(((SpigotNBTTagCompoundWrapper) tag).tag);
		}
	}

	@Override
	public ItemStackWrapper cloneItemStack() {
		return new SpigotItemStackWrapper(itemstack.cloneItemStack());
	}

	@Override
	public int hashCode() {
		return itemstack != null ? itemstack.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof SpigotItemStackWrapper)) {
			return false;
		}
		SpigotItemStackWrapper other = (SpigotItemStackWrapper) otherObj;
		return Objects.equals(itemstack, other.itemstack);
	}

	@Override
	public String toString() {
		return String.valueOf(itemstack);
	}

}
