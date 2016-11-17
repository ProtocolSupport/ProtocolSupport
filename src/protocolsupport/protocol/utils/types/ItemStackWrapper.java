package protocolsupport.protocol.utils.types;

import java.util.Objects;

import org.bukkit.Material;

import net.minecraft.server.v1_11_R1.Item;
import net.minecraft.server.v1_11_R1.ItemStack;

public class ItemStackWrapper {

	private ItemStack itemstack;

	private ItemStackWrapper(ItemStack itemstack) {
		this.itemstack = itemstack;
	}

	public static ItemStackWrapper createNull() {
		return new ItemStackWrapper(null);
	}

	@SuppressWarnings("deprecation")
	public static ItemStackWrapper create(Material material) {
		return ItemStackWrapper.create(material.getId());
	}

	public static ItemStackWrapper create(int typeId) {
		return new ItemStackWrapper(new ItemStack(Item.getById(typeId)));
	}

	public final ItemStack unwrap() {
		return itemstack;
	}

	public boolean isNull() {
		return (itemstack == null) || (itemstack.getItem() == null);
	}

	public int getTypeId() {
		return Item.getId(itemstack.getItem());
	}

	@SuppressWarnings("deprecation")
	public Material getType() {
		return Material.getMaterial(getTypeId());
	}

	@SuppressWarnings("deprecation")
	public void setTypeId(int typeId) {
		itemstack.setItem(Item.getById(typeId));
	}

	@SuppressWarnings("deprecation")
	public void setType(Material material) {
		setTypeId(material.getId());
	}

	public int getData() {
		return itemstack.getData();
	}

	public void setData(int data) {
		itemstack.setData(data);
	}

	public int getAmount() {
		return itemstack.getCount();
	}

	public void setAmount(int amount) {
		itemstack.setCount(amount);
	}

	public String getDisplayName() {
		return itemstack.getName();
	}

	public void setDisplayName(String displayName) {
		itemstack.c(displayName);
	}

	public NBTTagCompoundWrapper getTag() {
		return NBTTagCompoundWrapper.wrap(itemstack.getTag());
	}

	public void setTag(NBTTagCompoundWrapper tag) {
		itemstack.setTag(tag.unwrap());
	}

	public ItemStackWrapper cloneItemStack() {
		return new ItemStackWrapper(itemstack.cloneItemStack());
	}

	@Override
	public int hashCode() {
		return itemstack != null ? itemstack.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof ItemStackWrapper)) {
			return false;
		}
		ItemStackWrapper other = (ItemStackWrapper) otherObj;
		return Objects.equals(itemstack, other.itemstack);
	}

	@Override
	public String toString() {
		return String.valueOf(itemstack);
	}

}
