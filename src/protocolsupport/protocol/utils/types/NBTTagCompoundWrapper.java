package protocolsupport.protocol.utils.types;

import java.util.Objects;

import org.spigotmc.SneakyThrow;

import net.minecraft.server.v1_10_R1.MojangsonParseException;
import net.minecraft.server.v1_10_R1.MojangsonParser;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class NBTTagCompoundWrapper {

	private NBTTagCompound tag;

	private NBTTagCompoundWrapper(NBTTagCompound tag) {
		this.tag = tag;
	}

	public static NBTTagCompoundWrapper fromJson(String json) {
		try {
			return new NBTTagCompoundWrapper(MojangsonParser.parse(json));
		} catch (MojangsonParseException e) {
			SneakyThrow.sneaky(e);
		}
		return null;
	}

	public static NBTTagCompoundWrapper wrap(NBTTagCompound tag) {
		return new NBTTagCompoundWrapper(tag);
	}

	public static NBTTagCompoundWrapper createEmpty() {
		return new NBTTagCompoundWrapper(new NBTTagCompound());
	}

	public static NBTTagCompoundWrapper createNull() {
		return new NBTTagCompoundWrapper(null);
	}

	public final NBTTagCompound unwrap() {
		return tag;
	}

	public boolean isNull() {
		return tag == null;
	}

	public void remove(String key) {
		tag.remove(key);
	}

	public NBTTagCompoundWrapper getCompound(String key) {
		return new NBTTagCompoundWrapper(tag.getCompound(key));
	}

	public String getString(String key) {
		return tag.getString(key);
	}

	public void setString(String key, String value) {
		tag.setString(key, value);
	}

	public int getNumber(String key) {
		return tag.getInt(key);
	}

	public void setInt(String key, int i) {
		tag.setInt(key, i);
	}

	public void setByte(String key, int value) {
		tag.setByte(key, (byte) value);
	}

	@Override
	public int hashCode() {
		return tag != null ? tag.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof NBTTagCompoundWrapper)) {
			return false;
		}
		NBTTagCompoundWrapper other = (NBTTagCompoundWrapper) otherObj;
		return Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return tag.toString();
	}

}
