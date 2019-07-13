package protocolsupport.protocol.types;

import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.utils.Utils;

public class NetworkItemStack {

	public static final int DEFAULT_LEGACY_DATA = -1;

	protected int runtimeId;
	protected int amount;
	protected int legacyData = DEFAULT_LEGACY_DATA;
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
		final NetworkItemStack stack = new NetworkItemStack();
		stack.setTypeId(getTypeId());
		stack.setLegacyData(getLegacyData());
		stack.setAmount(getAmount());
		stack.setNBT(getNBT() == null ? null : getNBT().clone());
		return stack;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof NetworkItemStack) {
			final NetworkItemStack wrapper = (NetworkItemStack) object;
			if (wrapper.isNull() || isNull()) {
				return wrapper.isNull() == isNull();
			}
			final NBTCompound thatNBT = wrapper.getNBT();
			return  (wrapper.getTypeId() == getTypeId()) &&
					(wrapper.getLegacyData() == getLegacyData()) &&
					//(wrapper.getAmount() == getAmount()) &&
					(thatNBT == getNBT() || (thatNBT != null ? thatNBT.equals(getNBT()) : false));
		}
		return false;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

	@Override
	public int hashCode() {
		int hash = 0;
		if (!isNull()) {
			hash = getTypeId();
			hash = 524287 * hash + getLegacyData();
			//hash = 41 * hash + getAmount();
			hash = 31 * hash + (getNBT() == null ? 0 : getNBT().hashCode());
		}
		return hash;
	}

	public static final NetworkItemStack NULL = new NetworkItemStack() {

		@Override
		public boolean isNull() {
			return true;
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
