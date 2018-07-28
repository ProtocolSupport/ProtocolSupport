package protocolsupport.protocol.utils.networkentity;

import protocolsupport.utils.Utils;

public class NetworkEntityDataCache {

	protected byte baseFlags = 0;
	protected boolean firstMeta = true;

	public byte getBaseFlags() {
		return baseFlags;
	}

	public boolean getBaseFlag(int bitpos) {
		return (baseFlags & (1 << (bitpos - 1))) != 0;
	}

	public void setBaseFlag(int bitpos, boolean value) {
		setBaseFlag(bitpos, value ? 1 : 0);
	}

	public void setBaseFlag(int bitpos, int value) {
		baseFlags &= ~(1 << (bitpos - 1));
		baseFlags |= (value << (bitpos - 1));
	}

	public void setBaseFlags(byte baseFlags) {
		this.baseFlags = baseFlags;
	}

	public boolean isFirstMeta() {
		return firstMeta;
	}

	public void setFirstMeta(boolean firstMeta) {
		this.firstMeta = firstMeta;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}