package protocolsupport.zplatform.impl.glowstone.itemstack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Objects;

import net.glowstone.util.mojangson.Mojangson;
import net.glowstone.util.mojangson.ex.MojangsonParseException;
import net.glowstone.util.nbt.CompoundTag;
import net.glowstone.util.nbt.ListTag;
import net.glowstone.util.nbt.NBTInputStream;
import net.glowstone.util.nbt.NBTOutputStream;
import net.glowstone.util.nbt.NBTReadLimiter;
import net.glowstone.util.nbt.Tag;
import net.glowstone.util.nbt.TagType;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class GlowStoneNBTTagCompoundWrapper extends NBTTagCompoundWrapper {

	protected final CompoundTag tag;
	protected GlowStoneNBTTagCompoundWrapper(CompoundTag tag) {
		this.tag = tag;
	}

	public CompoundTag unwrap() {
		return tag;
	}

	public static GlowStoneNBTTagCompoundWrapper fromJson(String json) {
		try {
			return new GlowStoneNBTTagCompoundWrapper(Mojangson.parseCompound(json));
		} catch (MojangsonParseException e) {
			throw new RuntimeException("Unable to parse mojangson", e);
		}
	}

	@SuppressWarnings("resource")
	public static GlowStoneNBTTagCompoundWrapper fromStream(InputStream in) throws IOException {
		return new GlowStoneNBTTagCompoundWrapper(new NBTInputStream(in, false).readCompound(new NBTReadLimiter(2097152L)));
	}

	public static GlowStoneNBTTagCompoundWrapper createEmpty() {
		return new GlowStoneNBTTagCompoundWrapper(new CompoundTag());
	}

	public static GlowStoneNBTTagCompoundWrapper createNull() {
		return new GlowStoneNBTTagCompoundWrapper(null);
	}

	public static GlowStoneNBTTagCompoundWrapper wrap(CompoundTag tag) {
		return new GlowStoneNBTTagCompoundWrapper(tag);
	}

	@SuppressWarnings("resource")
	@Override
	public void writeToStream(OutputStream outputstream) throws IOException {
		new NBTOutputStream(outputstream, false).writeTag(tag);
	}

	@Override
	public boolean isNull() {
		return tag == null;
	}

	@Override
	public void remove(String key) {
		tag.remove(key);
	}

	@Override
	public Collection<String> getKeys() {
		return tag.getValue().keySet();
	}

	@Override
	public boolean hasKeyOfType(String tagname, int type) {
		Tag<?> tagval = tag.getValue().get(tagname);
		if (tagval != null && tagval.getType().getId() == type) {
			return true;
		}
		return false;
	}

	@Override
	public NBTTagCompoundWrapper getCompound(String key) {
		Tag<?> tagval = tag.getValue().get(key);
		if (tagval != null && tagval.getType() == TagType.COMPOUND) {
			return GlowStoneNBTTagCompoundWrapper.wrap((CompoundTag) tagval);
		}
		return GlowStoneNBTTagCompoundWrapper.createEmpty();
	}

	@Override
	public void setCompound(String key, NBTTagCompoundWrapper compound) {
		tag.putCompound(key, ((GlowStoneNBTTagCompoundWrapper) compound).tag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public NBTTagListWrapper getList(String key, int type) {
		Tag<?> tagval = tag.getValue().get(key);
		if (tagval != null && tagval.getType() == TagType.LIST) {
			return new GlowStoneNBTTagListWrapper((ListTag<Tag<?>>) tagval);
		}
		return GlowStoneNBTTagListWrapper.create();
	}

	@Override
	public void setList(String key, NBTTagListWrapper list) {
		ListTag<?> listtag = ((GlowStoneNBTTagListWrapper) list).tag;
		tag.putList(key, listtag.getType(), listtag.getValue());
	}

	@Override
	public String getString(String key) {
		Tag<?> tagval = tag.getValue().get(key);
		if (tagval != null && tagval.getType() == TagType.STRING) {
			return (String) tagval.getValue();
		}
		return "";
	}

	@Override
	public void setString(String key, String value) {
		tag.putString(key, value);
	}

	@Override
	public int getNumber(String key) {
		Tag<?> tagval = tag.getValue().get(key);
		if (tagval != null) {
			Object rawval = tagval.getValue();
			if (rawval instanceof Number) {
				return ((Number) rawval).intValue();
			}
		}
		return 0;
	}

	@Override
	public void setInt(String key, int i) {
		tag.putInt(key, i);
	}

	@Override
	public void setByte(String key, int value) {
		tag.putByte(key, value);
	}

	@Override
	public int hashCode() {
		return tag != null ? tag.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof GlowStoneNBTTagCompoundWrapper)) {
			return false;
		}
		GlowStoneNBTTagCompoundWrapper other = (GlowStoneNBTTagCompoundWrapper) otherObj;
		return Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return tag.toString();
	}

}
