package protocolsupport.protocol.types.nbt;

public class NBTEnd extends NBT {

	public static final NBTEnd INSTANCE = new NBTEnd();

	@Override
	public NBTType<NBTEnd> getType() {
		return NBTType.END;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
