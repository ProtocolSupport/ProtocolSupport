package protocolsupport.protocol.utils.types;

import net.minecraft.server.v1_9_R2.NBTTagCompound;

public class NBTTagCompoundWrapper {

	private NBTTagCompound tag;

	public final NBTTagCompound unwrap() {
		return tag;
	}

	public NBTTagCompoundWrapper() {
	}

	public NBTTagCompoundWrapper(NBTTagCompound tag) {
		this.tag = tag;
	}

	public NBTTagCompoundWrapper(boolean createTag) {
		if (createTag) {
			tag = new NBTTagCompound();
		}
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

	public int getNumberAsInt(String key) {
		return tag.getInt(key);
	}

	public void setInt(String key, int i) {
		tag.setInt(key, i);
	}

}
