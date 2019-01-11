package protocolsupport.protocol.utils.types.nbt;

import protocolsupport.utils.Utils;

public abstract class NBT implements Cloneable {

	public abstract NBTType<?> getType();

	@Override
	public abstract boolean equals(Object other);

	@Override
	public abstract int hashCode();

	@Override
	public NBT clone() {
		try {
			return (NBT) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Failed to clone NBT", e);
		}
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
