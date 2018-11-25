package protocolsupport.protocol.utils.types.nbt;

import protocolsupport.utils.Utils;

public abstract class NBT {

	public abstract NBTType<?> getType();

	@Override
	public abstract boolean equals(Object other);

	@Override
	public abstract int hashCode();

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
