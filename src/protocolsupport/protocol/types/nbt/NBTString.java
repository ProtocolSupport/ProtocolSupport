package protocolsupport.protocol.types.nbt;

public class NBTString extends NBT {

	@Override
	public NBTType<NBTString> getType() {
		return NBTType.STRING;
	}

	protected final String string;
	public NBTString(String string) {
		this.string = string;
	}

	public String getValue() {
		return string;
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof NBTString) && ((NBTString) other).string.equals(string);
	}

	@Override
	public int hashCode() {
		return string.hashCode();
	}

	public static String getValueOrNull(NBTString tag) {
		return getValueOrDefault(tag, null);
	}

	public static String getValueOrDefault(NBTString tag, String defaultVal) {
		return tag != null ? tag.getValue() : defaultVal;
	}

	@Override
	public NBTString clone() {
		return this;
	}

}
