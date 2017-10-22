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


	public static ItemStackWrapper NULL = new ItemStackWrapper() {

		@Override
		public boolean isNull() {
			return true;
		}

		@Override
		public ItemStackWrapper cloneItemStack() {
			return ItemStackWrapper.NULL;
		}

		private UnsupportedOperationException reject() {
			return new UnsupportedOperationException("Null itemstack");
		}

		@Override
		public void setTypeId(int typeId) {
			throw reject();
		}

		@Override
		public void setTag(NBTTagCompoundWrapper tag) {
			throw reject();
		}

		@Override
		public void setDisplayName(String displayName) {
			throw reject();
		}

		@Override
		public void setData(int data) {
			throw reject();
		}

		@Override
		public void setAmount(int amount) {
			throw reject();
		}

		@Override
		public int getTypeId() {
			throw reject();
		}

		@Override
		public NBTTagCompoundWrapper getTag() {
			throw reject();
		}

		@Override
		public int getData() {
			throw reject();
		}

		@Override
		public int getAmount() {
			throw reject();
		}

		@Override
		public ItemStack asBukkitMirror() {
			throw reject();
		}
	};

}
