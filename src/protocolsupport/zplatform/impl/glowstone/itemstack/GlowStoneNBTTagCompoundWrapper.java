package protocolsupport.zplatform.impl.glowstone.itemstack;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.glowstone.util.mojangson.Mojangson;
import net.glowstone.util.mojangson.ex.MojangsonParseException;
import net.glowstone.util.nbt.CompoundTag;
import net.glowstone.util.nbt.ListTag;
import net.glowstone.util.nbt.NBTInputStream;
import net.glowstone.util.nbt.NBTReadLimiter;
import net.glowstone.util.nbt.StringTag;
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

	@Override
	public void writeToStream(DataOutput outputstream) throws IOException {
		NBTOutputStream.writeTag(outputstream, tag);
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
		if ((tagval != null) && (tagval.getType().getId() == type)) {
			return true;
		}
		return false;
	}

	@Override
	public NBTTagCompoundWrapper getCompound(String key) {
		Tag<?> tagval = tag.getValue().get(key);
		if ((tagval != null) && (tagval.getType() == TagType.COMPOUND)) {
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
		if ((tagval != null) && (tagval.getType() == TagType.LIST)) {
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
		if ((tagval != null) && (tagval.getType() == TagType.STRING)) {
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
		return Objects.toString(tag);
	}

	private static final class NBTOutputStream {

		@SuppressWarnings("rawtypes")
		public static void writeTag(DataOutput os, Tag tag) throws IOException {
			writeTag(os, "", tag);
		}

		@SuppressWarnings("rawtypes")
		private static void writeTag(DataOutput os, String name, final Tag tag) throws IOException {
			final TagType type = tag.getType();
			final byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
			if (type == TagType.END) {
				throw new IOException("Named TAG_End not permitted.");
			}
			os.writeByte(type.getId());
			os.writeShort(nameBytes.length);
			os.write(nameBytes);
			writeTagPayload(os, tag);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private static void writeTagPayload(DataOutput os, final Tag tag) throws IOException {
			final TagType type = tag.getType();
			switch (type) {
				case BYTE: {
					os.writeByte((byte) tag.getValue());
					break;
				}
				case SHORT: {
					os.writeShort((short) tag.getValue());
					break;
				}
				case INT: {
					os.writeInt((int) tag.getValue());
					break;
				}
				case LONG: {
					os.writeLong((long) tag.getValue());
					break;
				}
				case FLOAT: {
					os.writeFloat((float) tag.getValue());
					break;
				}
				case DOUBLE: {
					os.writeDouble((double) tag.getValue());
					break;
				}
				case BYTE_ARRAY: {
					final byte[] bytes = (byte[]) tag.getValue();
					os.writeInt(bytes.length);
					os.write(bytes);
					break;
				}
				case STRING: {
					final byte[] bytes = ((StringTag) tag).getValue().getBytes(StandardCharsets.UTF_8);
					os.writeShort(bytes.length);
					os.write(bytes);
					break;
				}
				case LIST: {
					final ListTag<Tag> listTag = (ListTag<Tag>) tag;
					final List<Tag> tags = listTag.getValue();
					os.writeByte(listTag.getChildType().getId());
					os.writeInt(tags.size());
					for (final Tag child : tags) {
						writeTagPayload(os, child);
					}
					break;
				}
				case COMPOUND: {
					final Map<String, Tag> map = ((CompoundTag) tag).getValue();
					for (final Map.Entry<String, Tag> entry : map.entrySet()) {
						writeTag(os, entry.getKey(), entry.getValue());
					}
					os.writeByte(0);
					break;
				}
				case INT_ARRAY: {
					final int[] ints = (int[]) tag.getValue();
					os.writeInt(ints.length);
					for (final int value : ints) {
						os.writeInt(value);
					}
					break;
				}
				default: {
					throw new IOException("Invalid tag type: " + type + ".");
				}
			}
		}

	}

}
