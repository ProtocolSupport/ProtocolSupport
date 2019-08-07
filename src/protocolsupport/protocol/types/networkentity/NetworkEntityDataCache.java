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

	protected long baseFlags = 0;

	public long getBaseFlags() {
		return baseFlags;
	}

	public void setBaseFlags(long baseFlags) {
		this.baseFlags = baseFlags;
	}

	public void setBaseFlag(int bitpos, int value) {
		baseFlags = BitUtils.setBit(baseFlags, bitpos, value);
	}


	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}