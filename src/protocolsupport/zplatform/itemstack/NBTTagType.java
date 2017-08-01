package protocolsupport.zplatform.itemstack;

public enum NBTTagType {

	END, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BYTE_ARRAY, STRING, LIST, COMPOUND, INT_ARRAY, LONG_ARRAY;

	public int getId() {
		return ordinal();
	}

	public static NBTTagType fromId(int id) {
		return NBTTagType.values()[id];
	}

}
