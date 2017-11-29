package protocolsupport.zplatform.itemstack;

import protocolsupport.protocol.utils.EnumConstantLookups;

public enum NBTTagType {

	END, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BYTE_ARRAY, STRING, LIST, COMPOUND, INT_ARRAY, LONG_ARRAY;

	public int getId() {
		return ordinal();
	}

	private static final EnumConstantLookups.EnumConstantLookup<NBTTagType> lookup = new EnumConstantLookups.EnumConstantLookup<>(NBTTagType.class);

	public static NBTTagType fromId(int id) {
		return lookup.getByOrdinal(id);
	}

}
