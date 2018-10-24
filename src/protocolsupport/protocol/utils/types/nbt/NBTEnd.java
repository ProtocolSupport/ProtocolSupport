package protocolsupport.protocol.utils.types.nbt;

public class NBTEnd extends NBT {

	public static final NBTEnd INSTANCE = new NBTEnd();

	@Override
	public NBTType<NBTEnd> getType() {
		return NBTType.END;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof NBTEnd;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
