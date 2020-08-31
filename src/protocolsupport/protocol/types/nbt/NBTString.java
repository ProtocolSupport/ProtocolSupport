package protocolsupport.protocol.types.nbt;

import java.util.Objects;

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
		NBTString other = (NBTString) obj;
		return Objects.equals(string, other.string);
	}

	@Override
	public int hashCode() {
		return Objects.hash(string);
	}

}
