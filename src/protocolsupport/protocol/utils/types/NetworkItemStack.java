package protocolsupport.protocol.utils.types;

import protocolsupport.protocol.utils.types.nbt.NBTCompound;

public class NetworkItemStack {

	protected int runtimeId;
	protected int amount;
	protected int legacyData;
	protected NBTCompound nbt;

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

	public NBTCompound getNBT() {
		return nbt;
	}

	public void setNBT(NBTCompound nbt) {
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
		stack.setLegacyData(getLegacyData());
		stack.setAmount(getAmount());
		stack.setNBT(getNBT());
		return stack;
	}

	@Override
	public String toString() {
		return "NetworkItemStack(" + getTypeId() + ":" + getLegacyData() + "," + getAmount() + ")";
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
		public void setNBT(NBTCompound tag) {
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
		public NBTCompound getNBT() {
			throw reject();
		}

		@Override
		public int getAmount() {
			throw reject();
		}

		@Override
		public String toString() {
			return "NetworkItemStack.NULL";
		}

	};

}
