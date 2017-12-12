package protocolsupport.zplatform.itemstack;

import java.util.Collection;

public abstract class NBTTagCompoundWrapper {

	public abstract boolean isNull();

	public abstract void remove(String key);

	public abstract Collection<String> getKeys();

	public abstract NBTTagType getTagType(String tagname);

	public abstract boolean hasKeyOfType(String tagname, NBTTagType type);

	public abstract NBTTagCompoundWrapper getCompound(String key);

	public abstract NBTTagListWrapper getList(String key);

	public abstract NBTTagListWrapper getList(String key, NBTTagType type);

	public abstract String getString(String key);

	public abstract int getIntNumber(String key);

	public abstract byte getByteNumber(String key);

	public abstract long getLongNumber(String key);

	public abstract float getFloatNumber(String key);

	public abstract double getDoubleNumber(String key);

	public abstract byte[] getByteArray(String key);

	public abstract int[] getIntArray(String key);

	public abstract long[] getLongArray(String key);

	public abstract void setCompound(String key, NBTTagCompoundWrapper compound);

	public abstract void setList(String key, NBTTagListWrapper list);

	public abstract void setString(String key, String value);

	public abstract void setByte(String key, int value);

	public abstract void setShort(String key, int value);

	public abstract void setInt(String key, int value);

	public abstract void setLong(String key, long value);

	public abstract void setFloat(String key, float value);

	public abstract void setDouble(String key, double value);

	public abstract void setByteArray(String key, byte[] value);

	public abstract void setIntArray(String key, int[] value);

	public abstract void setLongArray(String key, long[] value);

	public static final NBTTagCompoundWrapper NULL = new NBTTagCompoundWrapper() {

		private UnsupportedOperationException reject() {
			return new UnsupportedOperationException("Null tag");
		}

		@Override
		public boolean isNull() {
			return true;
		}

		@Override
		public void remove(String key) {
			throw reject();
		}

		@Override
		public Collection<String> getKeys() {
			throw reject();
		}

		@Override
		public NBTTagType getTagType(String tagname) {
			throw reject();
		}

		@Override
		public boolean hasKeyOfType(String tagname, NBTTagType type) {
			throw reject();
		}

		@Override
		public NBTTagCompoundWrapper getCompound(String key) {
			throw reject();
		}

		@Override
		public NBTTagListWrapper getList(String key) {
			throw reject();
		}

		@Override
		public NBTTagListWrapper getList(String key, NBTTagType type) {
			throw reject();
		}

		@Override
		public String getString(String key) {
			throw reject();
		}

		@Override
		public int getIntNumber(String key) {
			throw reject();
		}

		@Override
		public byte getByteNumber(String key) {
			throw reject();
		}

		@Override
		public long getLongNumber(String key) {
			throw reject();
		}

		@Override
		public float getFloatNumber(String key) {
			throw reject();
		}

		@Override
		public double getDoubleNumber(String key) {
			throw reject();
		}

		@Override
		public byte[] getByteArray(String key) {
			throw reject();
		}

		@Override
		public int[] getIntArray(String key) {
			throw reject();
		}

		@Override
		public long[] getLongArray(String key) {
			throw reject();
		}

		@Override
		public void setCompound(String key, NBTTagCompoundWrapper compound) {
			throw reject();
		}

		@Override
		public void setList(String key, NBTTagListWrapper list) {
			throw reject();
		}

		@Override
		public void setString(String key, String value) {
			throw reject();
		}

		@Override
		public void setInt(String key, int i) {
			throw reject();
		}

		@Override
		public void setByte(String key, int value) {
			throw reject();
		}

		@Override
		public void setShort(String key, int value) {
			throw reject();
		}

		@Override
		public void setLong(String key, long value) {
			throw reject();
		}

		@Override
		public void setFloat(String key, float value) {
			throw reject();
		}

		@Override
		public void setDouble(String key, double value) {
			throw reject();
		}

		@Override
		public void setByteArray(String key, byte[] value) {
			throw reject();
		}

		@Override
		public void setIntArray(String key, int[] value) {
			throw reject();
		}

		@Override
		public void setLongArray(String key, long[] value) {
			throw reject();
		}

	};

}
