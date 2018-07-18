package protocolsupport.zplatform.itemstack;

public class NetworkItemStack {

	protected int runtimeId;
	protected int amount;
	protected int legacyData;
	protected NBTTagCompoundWrapper nbt;

	public boolean isNull() {
		return false;
	}

	public int getTypeId() {
		return runtimeId;
	}

	public void setTypeId(int typeId) {
		this.runtimeId = typeId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public NBTTagCompoundWrapper getTag() {
		return nbt;
	}

	public void setTag(NBTTagCompoundWrapper nbt) {
		this.nbt = nbt;
	}

	public int getLegacyData() {
		return legacyData;
	}

	public void setLegacyData(int legacyData) {
		this.legacyData = legacyData;
	}

	public NetworkItemStack cloneItemStack() {
		NetworkItemStack stack = new NetworkItemStack();
		stack.setTypeId(getTypeId());
		stack.setAmount(getAmount());
		stack.setTag(getTag());
		return stack;
	}


	public static final NetworkItemStack NULL = new NetworkItemStack() {

		@Override
		public boolean isNull() {
			return true;
		}

		@Override
		public NetworkItemStack cloneItemStack() {
			return NetworkItemStack.NULL;
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
		public int getAmount() {
			throw reject();
		}

	};

}
