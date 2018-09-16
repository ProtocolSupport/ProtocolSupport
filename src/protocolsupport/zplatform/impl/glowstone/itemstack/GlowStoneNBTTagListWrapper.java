//package protocolsupport.zplatform.impl.glowstone.itemstack;
//
//import java.util.Collections;
//import java.util.Objects;
//
//import net.glowstone.util.nbt.ByteArrayTag;
//import net.glowstone.util.nbt.ByteTag;
//import net.glowstone.util.nbt.CompoundTag;
//import net.glowstone.util.nbt.DoubleTag;
//import net.glowstone.util.nbt.FloatTag;
//import net.glowstone.util.nbt.IntArrayTag;
//import net.glowstone.util.nbt.IntTag;
//import net.glowstone.util.nbt.ListTag;
//import net.glowstone.util.nbt.LongTag;
//import net.glowstone.util.nbt.ShortTag;
//import net.glowstone.util.nbt.StringTag;
//import net.glowstone.util.nbt.Tag;
//import net.glowstone.util.nbt.TagType;
//import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
//import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
//import protocolsupport.zplatform.itemstack.NBTTagType;
//
//public class GlowStoneNBTTagListWrapper extends NBTTagListWrapper {
//
//	protected final ListTag<Tag<?>> tag;
//	protected GlowStoneNBTTagListWrapper(ListTag<Tag<?>> tag) {
//		this.tag = tag;
//	}
//
//	public static GlowStoneNBTTagListWrapper create() {
//		return new GlowStoneNBTTagListWrapper(new ListTag<>(TagType.END, Collections.emptyList()));
//	}
//
//	@Override
//	public NBTTagType getType() {
//		return NBTTagType.fromId(tag.getChildType().getId());
//	}
//
//	@Override
//	public boolean isEmpty() {
//		return tag.getValue().isEmpty();
//	}
//
//	@Override
//	public int size() {
//		return tag.getValue().size();
//	}
//
//	private boolean checkIndex(int index) {
//		return (index >= 0) && (index < size());
//	}
//
//	@Override
//	public NBTTagCompoundWrapper getCompound(int index) {
//		if ((tag.getChildType() == TagType.COMPOUND) && checkIndex(index)) {
//			return GlowStoneNBTTagCompoundWrapper.wrap((CompoundTag) tag.getValue().get(index));
//		}
//		return GlowStoneNBTTagCompoundWrapper.createEmpty();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public NBTTagListWrapper getList(int index) {
//		if ((tag.getChildType() == TagType.LIST) && checkIndex(index)) {
//			return new GlowStoneNBTTagListWrapper((ListTag<Tag<?>>) tag.getValue().get(index));
//		}
//		return GlowStoneNBTTagListWrapper.create();
//	}
//
//	@Override
//	public String getString(int index) {
//		if ((tag.getChildType() == TagType.STRING) && checkIndex(index)) {
//			return ((StringTag) tag.getValue().get(index)).getValue();
//		}
//		return "";
//	}
//
//	private Number getNumber(int index) {
//		if (checkIndex(index)) {
//			Object value = tag.getValue().get(index).getValue();
//			if (value instanceof Number) {
//				return (Number) value;
//			}
//		}
//		return Integer.valueOf(0);
//	}
//
//	@Override
//	public int getIntNumber(int index) {
//		return getNumber(index).intValue();
//	}
//
//	@Override
//	public long getLongNumber(int index) {
//		return getNumber(index).longValue();
//	}
//
//	@Override
//	public float getFloatNumber(int index) {
//		return getNumber(index).floatValue();
//	}
//
//	@Override
//	public double getDoubleNumber(int index) {
//		return getNumber(index).doubleValue();
//	}
//
//	@Override
//	public byte[] getByteArray(int index) {
//		if ((tag.getChildType() == TagType.BYTE_ARRAY) && checkIndex(index)) {
//			return ((ByteArrayTag) tag.getValue().get(index)).getValue();
//		}
//		return new byte[0];
//	}
//
//	@Override
//	public int[] getIntArray(int index) {
//		if ((tag.getChildType() == TagType.BYTE_ARRAY) && checkIndex(index)) {
//			return ((IntArrayTag) tag.getValue().get(index)).getValue();
//		}
//		return new int[0];
//	}
//
//	@Override
//	public long[] getLongArray(int index) {
//		//TODO: actually implement this
//		return new long[0];
//	}
//
//	@Override
//	public void addCompound(NBTTagCompoundWrapper wrapper) {
//		if (tag.getChildType() == TagType.COMPOUND) {
//			tag.getValue().add(((GlowStoneNBTTagCompoundWrapper) wrapper).tag);
//		}
//	}
//
//	@Override
//	public void addList(NBTTagListWrapper wrapper) {
//		if (tag.getChildType() == TagType.LIST) {
//			tag.getValue().add(((GlowStoneNBTTagListWrapper) wrapper).tag);
//		}
//	}
//
//	@Override
//	public void addString(String value) {
//		if (tag.getChildType() == TagType.STRING) {
//			tag.getValue().add(new StringTag(value));
//		}
//	}
//
//	@Override
//	public void addByte(int b) {
//		if (tag.getChildType() == TagType.BYTE) {
//			tag.getValue().add(new ByteTag((byte) b));
//		}
//	}
//
//	@Override
//	public void addShort(int value) {
//		if (tag.getChildType() == TagType.SHORT) {
//			tag.getValue().add(new ShortTag((short) value));
//		}
//	}
//
//	@Override
//	public void addInt(int i) {
//		if (tag.getChildType() == TagType.INT) {
//			tag.getValue().add(new IntTag(i));
//		}
//	}
//
//	@Override
//	public void addLong(long value) {
//		if (tag.getChildType() == TagType.LONG) {
//			tag.getValue().add(new LongTag(value));
//		}
//	}
//
//	@Override
//	public void addFloat(float value) {
//		if (tag.getChildType() == TagType.FLOAT) {
//			tag.getValue().add(new FloatTag(value));
//		}
//	}
//
//	@Override
//	public void addDouble(double value) {
//		if (tag.getChildType() == TagType.DOUBLE) {
//			tag.getValue().add(new DoubleTag(value));
//		}
//	}
//
//	@Override
//	public void addByteArray(byte[] value) {
//		if (tag.getChildType() == TagType.BYTE_ARRAY) {
//			tag.getValue().add(new ByteArrayTag(value));
//		}
//	}
//
//	@Override
//	public void addIntArray(int[] value) {
//		if (tag.getChildType() == TagType.INT_ARRAY) {
//			tag.getValue().add(new IntArrayTag(value));
//		}
//	}
//
//	@Override
//	public void addLongArray(long[] value) {
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
//		if (!(otherObj instanceof GlowStoneNBTTagListWrapper)) {
//			return false;
//		}
//		GlowStoneNBTTagListWrapper other = (GlowStoneNBTTagListWrapper) otherObj;
//		return Objects.equals(tag, other.tag);
//	}
//
//	@Override
//	public String toString() {
//		return tag.toString();
//	}
//
//}
