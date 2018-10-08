package protocolsupport.protocol.utils.types.nbt;

public abstract class NBT {

	public abstract NBTType<?> getType();

	@Override
	public abstract boolean equals(Object other);

	@Override
	public abstract int hashCode();

}
