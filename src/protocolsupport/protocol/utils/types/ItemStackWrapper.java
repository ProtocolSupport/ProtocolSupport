package protocolsupport.protocol.utils.types;

import java.util.Objects;

import org.bukkit.Material;

import net.minecraft.server.v1_10_R1.Item;
import net.minecraft.server.v1_10_R1.ItemStack;

public class ItemStackWrapper {

	private ItemStack itemstack;

	public final ItemStack unwrap() {
		return itemstack;
	}

	public ItemStackWrapper() {
	}

	public ItemStackWrapper(ItemStack itemstack) {
		this.itemstack = itemstack;
	}

	@SuppressWarnings("deprecation")
	public ItemStackWrapper(Material material) {
		this(new ItemStack(Item.getById(material.getId())));
	}

	public boolean isNull() {
		return itemstack == null || itemstack.getItem() == null;
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
		return itemstack.count;
	}

	public void setAmount(int amount) {
		itemstack.count = amount;
	}

	public String getDisplayName() {
		return itemstack.getName();
	}

	public void setDisplayName(String displayName) {
		itemstack.c(displayName);
	}

	public NBTTagCompoundWrapper getTag() {
		return new NBTTagCompoundWrapper(itemstack.getTag());
	}

	public void setTag(NBTTagCompoundWrapper tag) {
		itemstack.setTag(tag.unwrap());
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

}
