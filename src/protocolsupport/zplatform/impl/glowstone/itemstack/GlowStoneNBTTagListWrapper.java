package protocolsupport.zplatform.impl.glowstone.itemstack;

import java.util.Collections;
import java.util.Objects;

import net.glowstone.util.nbt.ByteTag;
import net.glowstone.util.nbt.CompoundTag;
import net.glowstone.util.nbt.IntTag;
import net.glowstone.util.nbt.ListTag;
import net.glowstone.util.nbt.StringTag;
import net.glowstone.util.nbt.Tag;
import net.glowstone.util.nbt.TagType;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class GlowStoneNBTTagListWrapper extends NBTTagListWrapper {

	protected final ListTag<Tag<?>> tag;
	protected GlowStoneNBTTagListWrapper(ListTag<Tag<?>> tag) {
		this.tag = tag;
	}

	public static GlowStoneNBTTagListWrapper create() {
		return new GlowStoneNBTTagListWrapper(new ListTag<>(null, Collections.emptyList()));
	}

	@Override
	public boolean isEmpty() {
		return tag.getValue().isEmpty();
	}

	@Override
	public int size() {
		return tag.getValue().size();
	}

	private boolean checkIndex(int index) {
		return index >= 0 && index < size();
	}

	@Override
	public NBTTagCompoundWrapper getCompound(int index) {
		if (tag.getType() == TagType.COMPOUND && checkIndex(index)) {
			return GlowStoneNBTTagCompoundWrapper.wrap((CompoundTag) tag.getValue().get(index));
		}
		return GlowStoneNBTTagCompoundWrapper.createEmpty();
	}

	@Override
	public void addCompound(NBTTagCompoundWrapper wrapper) {
		if (tag.getType() == TagType.COMPOUND) {
			tag.getValue().add(((GlowStoneNBTTagCompoundWrapper) wrapper).tag);
		}
	}

	@Override
	public String getString(int index) {
		if (tag.getType() == TagType.STRING && checkIndex(index)) {
			return ((StringTag) tag.getValue().get(index)).getValue();
		}
		return "";
	}

	@Override
	public void addString(String value) {
		if (tag.getType() == TagType.STRING) {
			tag.getValue().add(new StringTag(value));
		}
	}

	@Override
	public int getNumber(int index) {
		if (checkIndex(index)) {
			Object value = tag.getValue().get(index).getValue();
			if (value instanceof Number) {
				return ((Number) value).intValue();
			}
		}
		return 0;
	}

	@Override
	public void addInt(int i) {
		if (tag.getType() == TagType.INT) {
			tag.getValue().add(new IntTag(i));
		}
	}

	@Override
	public void addByte(int b) {
		if (tag.getType() == TagType.BYTE) {
			tag.getValue().add(new ByteTag((byte) b));
		}
	}

	@Override
	public int hashCode() {
		return tag != null ? tag.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof GlowStoneNBTTagListWrapper)) {
			return false;
		}
		GlowStoneNBTTagListWrapper other = (GlowStoneNBTTagListWrapper) otherObj;
		return Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return tag.toString();
	}

}
