//package protocolsupport.zplatform.impl.glowstone.itemstack;
//
//import java.util.Collection;
//import java.util.Objects;
//
//import net.glowstone.util.mojangson.Mojangson;
//import net.glowstone.util.mojangson.ex.MojangsonParseException;
//import net.glowstone.util.nbt.CompoundTag;
//import net.glowstone.util.nbt.ListTag;
//import net.glowstone.util.nbt.Tag;
//import net.glowstone.util.nbt.TagType;
//import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
//import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
//import protocolsupport.zplatform.itemstack.NBTTagType;
//
//public class GlowStoneNBTTagCompoundWrapper extends NBTTagCompoundWrapper {
//
//	protected final CompoundTag tag;
//
//	protected GlowStoneNBTTagCompoundWrapper(CompoundTag tag) {
//		this.tag = tag;
//	}
//
//	public static GlowStoneNBTTagCompoundWrapper fromJson(String json) {
//		try {
//			return new GlowStoneNBTTagCompoundWrapper(Mojangson.parseCompound(json));
//		} catch (MojangsonParseException e) {
//			throw new RuntimeException("Unable to parse mojangson", e);
//		}
//	}
//
//	public static GlowStoneNBTTagCompoundWrapper createEmpty() {
//		return new GlowStoneNBTTagCompoundWrapper(new CompoundTag());
//	}
//
//	public static GlowStoneNBTTagCompoundWrapper wrap(CompoundTag tag) {
//		return new GlowStoneNBTTagCompoundWrapper(tag);
//	}
//
//	@Override
//	public CompoundTag unwrap() {
//		return tag;
//	}
//
//	@Override
//	public boolean isNull() {
//		return tag == null;
//	}
//
//	@Override
//	public void remove(String key) {
//		tag.remove(key);
//	}
//
//	@Override
//	public Collection<String> getKeys() {
//		return tag.getValue().keySet();
//	}
//
//	@Override
//	public NBTTagType getTagType(String tagname) {
//		Tag<?> tagval = tag.getValue().get(tagname);
//		return tagval != null ? NBTTagType.fromId(tagval.getType().getId()) : null;
//	}
//
//	@Override
//	public boolean hasKeyOfType(String tagname, NBTTagType type) {
//		Tag<?> tagval = tag.getValue().get(tagname);
//		return ((tagval != null) && (tagval.getType().getId() == type.getId()));
//	}
//
//	@Override
//	public NBTTagCompoundWrapper getCompound(String key) {
//		Tag<?> tagval = tag.getValue().get(key);
//		if ((tagval != null) && (tagval.getType() == TagType.COMPOUND)) {
//			return GlowStoneNBTTagCompoundWrapper.wrap((CompoundTag) tagval);
//		}
//		return GlowStoneNBTTagCompoundWrapper.createEmpty();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public NBTTagListWrapper getList(String key) {
//		Tag<?> tagval = tag.getValue().get(key);
//		if ((tagval != null) && (tagval.getType() == TagType.LIST)) {
//			return new GlowStoneNBTTagListWrapper((ListTag<Tag<?>>) tagval);
//		}
//		return GlowStoneNBTTagListWrapper.create();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public NBTTagListWrapper getList(String key, NBTTagType type) {
//		Tag<?> tagval = tag.getValue().get(key);
//		if ((tagval != null) && (tagval.getType() == TagType.LIST)) {
//			ListTag<Tag<?>> listTag = (ListTag<Tag<?>>) tagval;
//			if (listTag.getChildType().getId() == type.getId()) {
//				return new GlowStoneNBTTagListWrapper(listTag);
//			}
//		}
//		return GlowStoneNBTTagListWrapper.create();
//	}
//
//	@Override
//	public String getString(String key) {
//		Tag<?> tagval = tag.getValue().get(key);
//		if ((tagval != null) && (tagval.getType() == TagType.STRING)) {
//			return (String) tagval.getValue();
//		}
//		return "";
//	}
//
//	private Number getNumber(String key) {
//		Tag<?> tagval = tag.getValue().get(key);
//		if (tagval != null) {
//			Object rawval = tagval.getValue();
//			if (rawval instanceof Number) {
//				return (Number) rawval;
//			}
//		}
//		return Integer.valueOf(0);
//	}
//
//	@Override
//	public int getIntNumber(String key) {
//		return getNumber(key).intValue();
//	}
//
//	@Override
//	public long getLongNumber(String key) {
//		return getNumber(key).longValue();
//	}
//
//	@Override
//	public float getFloatNumber(String key) {
//		return getNumber(key).floatValue();
//	}
//
//	@Override
//	public double getDoubleNumber(String key) {
//		return getNumber(key).doubleValue();
//	}
//
//	@Override
//	public byte[] getByteArray(String key) {
//		Tag<?> tagval = tag.getValue().get(key);
//		if ((tagval != null) && (tagval.getType() == TagType.BYTE_ARRAY)) {
//			return (byte[]) tagval.getValue();
//		}
//		return new byte[0];
//	}
//
//	@Override
//	public int[] getIntArray(String key) {
//		Tag<?> tagval = tag.getValue().get(key);
//		if ((tagval != null) && (tagval.getType() == TagType.INT_ARRAY)) {
//			return (int[]) tagval.getValue();
//		}
//		return new int[0];
//	}
//
//	@Override
//	public long[] getLongArray(String key) {
//		//TODO: actually implement this
//		return new long[0];
//	}
//
//	@Override
//	public void setCompound(String key, NBTTagCompoundWrapper compound) {
//		tag.putCompound(key, ((GlowStoneNBTTagCompoundWrapper) compound).tag);
//	}
//
//	@Override
//	public void setList(String key, NBTTagListWrapper list) {
//		tag.getValue().put(key, ((GlowStoneNBTTagListWrapper) list).tag);
//	}
//
//	@Override
//	public void setString(String key, String value) {
//		tag.putString(key, value);
//	}
//
//	@Override
//	public void setByte(String key, int value) {
//		tag.putByte(key, value);
//	}
//
//	@Override
//	public void setShort(String key, int value) {
//		tag.putShort(key, value);
//	}
//
//	@Override
//	public void setInt(String key, int i) {
//		tag.putInt(key, i);
//	}
//
//	@Override
//	public void setLong(String key, long value) {
//		tag.putLong(key, value);
//	}
//
//	@Override
//	public void setFloat(String key, float value) {
//		tag.putFloat(key, value);
//	}
//
//	@Override
//	public void setDouble(String key, double value) {
//		tag.putDouble(key, value);
//	}
//
//	@Override
//	public void setByteArray(String key, byte[] value) {
//		tag.putByteArray(key, value);
//	}
//
//	@Override
//	public void setIntArray(String key, int[] value) {
//		tag.putIntArray(key, value);
//	}
//
//	@Override
//	public void setLongArray(String key, long[] value) {
//		//TODO: actually implement this
//	}
//
//	@Override
//	public int hashCode() {
//		return tag != null ? tag.hashCode() : 0;
//	}
//
//	@Override
//	public boolean equals(Object otherObj) {
//		if (!(otherObj instanceof GlowStoneNBTTagCompoundWrapper)) {
//			return false;
//		}
//		GlowStoneNBTTagCompoundWrapper other = (GlowStoneNBTTagCompoundWrapper) otherObj;
//		return Objects.equals(tag, other.tag);
//	}
//
//	@Override
//	public String toString() {
//		return Objects.toString(tag);
//	}
//
//}
