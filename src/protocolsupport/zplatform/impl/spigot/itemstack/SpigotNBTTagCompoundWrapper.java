package protocolsupport.zplatform.impl.spigot.itemstack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import org.spigotmc.SneakyThrow;

import net.minecraft.server.v1_11_R1.MojangsonParseException;
import net.minecraft.server.v1_11_R1.MojangsonParser;
import net.minecraft.server.v1_11_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_11_R1.NBTReadLimiter;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class SpigotNBTTagCompoundWrapper extends NBTTagCompoundWrapper {

	protected final NBTTagCompound tag;
	protected SpigotNBTTagCompoundWrapper(NBTTagCompound tag) {
		this.tag = tag;
	}

	public final NBTTagCompound unwrap() {
		return tag;
	}

	public static NBTTagCompoundWrapper fromJson(String json) {
		try {
			return new SpigotNBTTagCompoundWrapper(MojangsonParser.parse(json));
		} catch (MojangsonParseException e) {
			SneakyThrow.sneaky(e);
		}
		return null;
	}

	public static SpigotNBTTagCompoundWrapper fromStream(DataInput datainput) throws IOException {
		return new SpigotNBTTagCompoundWrapper(NBTCompressedStreamTools.a(datainput, new NBTReadLimiter(2097152L)));
	}

	public static SpigotNBTTagCompoundWrapper createEmpty() {
		return new SpigotNBTTagCompoundWrapper(new NBTTagCompound());
	}

	public static SpigotNBTTagCompoundWrapper createNull() {
		return new SpigotNBTTagCompoundWrapper(null);
	}

	public static SpigotNBTTagCompoundWrapper wrap(NBTTagCompound tag) {
		return new SpigotNBTTagCompoundWrapper(tag);
	}

	@Override
	public void writeToStream(DataOutput outputstream) throws IOException {
		NBTCompressedStreamTools.a(tag, outputstream);
	}

	@Override
	public boolean isNull() {
		return tag == null;
	}

	@Override
	public void remove(String key) {
		tag.remove(key);
	}

	public static final int TYPE_STRING = 8;
	public static final int TYPE_COMPOUND = 10;
	public static final int TYPE_LIST = 9;

	@Override
	public Collection<String> getKeys() {
		return tag.c();
	}

	@Override
	public boolean hasKeyOfType(String tagname, int i) {
		return tag.hasKeyOfType(tagname, i);
	}

	@Override
	public NBTTagCompoundWrapper getCompound(String key) {
		return new SpigotNBTTagCompoundWrapper(tag.getCompound(key));
	}

	@Override
	public void setCompound(String key, NBTTagCompoundWrapper compound) {
		tag.set(key, ((SpigotNBTTagCompoundWrapper) compound).tag);
	}

	@Override
	public NBTTagListWrapper getList(String key, int type) {
		return new SpigotNBTTagListWrapper(tag.getList(key, type));
	}

	@Override
	public void setList(String key, NBTTagListWrapper list) {
		tag.set(key, ((SpigotNBTTagListWrapper) list).tag);
	}

	@Override
	public String getString(String key) {
		return tag.getString(key);
	}

	@Override
	public void setString(String key, String value) {
		tag.setString(key, value);
	}

	@Override
	public int getNumber(String key) {
		return tag.getInt(key);
	}

	@Override
	public void setInt(String key, int i) {
		tag.setInt(key, i);
	}

	@Override
	public void setByte(String key, int value) {
		tag.setByte(key, (byte) value);
	}

	@Override
	public int hashCode() {
		return tag != null ? tag.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof SpigotNBTTagCompoundWrapper)) {
			return false;
		}
		SpigotNBTTagCompoundWrapper other = (SpigotNBTTagCompoundWrapper) otherObj;
		return Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return Objects.toString(tag);
	}

}
