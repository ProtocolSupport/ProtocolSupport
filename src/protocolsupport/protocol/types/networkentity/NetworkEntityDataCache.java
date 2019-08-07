package protocolsupport.protocol.types.networkentity;

import protocolsupport.utils.BitUtils;
import protocolsupport.utils.Utils;

public class NetworkEntityDataCache {

	protected boolean firstMeta = true;

	public boolean isFirstMeta() {
		return firstMeta;
	}

	public void unsetFirstMeta() {
		this.firstMeta = false;
	}

	protected int baseFlags = 0;

	public int getBaseFlags() {
		return baseFlags;
	}

	public void setBaseFlags(int baseFlags) {
		this.baseFlags = baseFlags;
	}

	public void setBaseFlag(int bitpos, int value) {
		baseFlags = BitUtils.setIBit(baseFlags, bitpos, value);
	}


	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}