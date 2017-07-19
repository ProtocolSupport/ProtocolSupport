package protocolsupport.zplatform.impl.glowstone.itemstack;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.glowstone.inventory.GlowItemFactory;
import net.glowstone.util.InventoryUtil;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class GlowStoneItemStackWrapper extends ItemStackWrapper {

	protected final ItemStack itemstack;
	protected GlowStoneItemStackWrapper(ItemStack itemstack) {
		this.itemstack = itemstack;
	}

	public static ItemStackWrapper createNull() {
		return new GlowStoneItemStackWrapper(InventoryUtil.createEmptyStack());
	}

	@SuppressWarnings("deprecation")
	public static ItemStackWrapper create(int typeId) {
		return new GlowStoneItemStackWrapper(new ItemStack(typeId));
	}

	@Override
	public ItemStack asBukkitMirror() {
		return itemstack;
	}

	@Override
	public boolean isNull() {
		return (itemstack.getType() == Material.AIR) || (getAmount() <= 0) || (getData() < -32768) || (getData() > 65535);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getTypeId() {
		return itemstack.getTypeId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setTypeId(int typeId) {
		itemstack.setTypeId(typeId);
	}

	@Override
	public int getData() {
		return itemstack.getDurability() & 0xFFFF;
	}

	@Override
	public void setData(int data) {
		itemstack.setDurability((short) data);
	}

	@Override
	public int getAmount() {
		return itemstack.getAmount();
	}

	@Override
	public void setAmount(int amount) {
		itemstack.setAmount(amount);
	}

	@Override
	public void setDisplayName(String displayName) {
		ItemMeta meta = itemstack.getItemMeta();
		if (meta == null) {
			meta = Bukkit.getItemFactory().getItemMeta(getType());
		}
		if (meta != null) {
			meta.setDisplayName(displayName);
			itemstack.setItemMeta(meta);
		}
	}

	@Override
	public NBTTagCompoundWrapper getTag() {
		return GlowStoneNBTTagCompoundWrapper.wrap(GlowItemFactory.instance().writeNbt(itemstack.getItemMeta()));
	}

	@Override
	public void setTag(NBTTagCompoundWrapper tag) {
		itemstack.setItemMeta(GlowItemFactory.instance().readNbt(getType(), ((GlowStoneNBTTagCompoundWrapper) tag).tag));
	}

	@Override
	public ItemStackWrapper cloneItemStack() {
		return new GlowStoneItemStackWrapper(itemstack.clone());
	}

	@Override
	public int hashCode() {
		return itemstack != null ? itemstack.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof GlowStoneItemStackWrapper)) {
			return false;
		}
		GlowStoneItemStackWrapper other = (GlowStoneItemStackWrapper) otherObj;
		return Objects.equals(itemstack, other.itemstack);
	}

	@Override
	public String toString() {
		return String.valueOf(itemstack);
	}

}
