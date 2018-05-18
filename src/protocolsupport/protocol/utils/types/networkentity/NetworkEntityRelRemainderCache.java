package protocolsupport.protocol.utils.types.networkentity;

import protocolsupport.protocol.utils.types.Position;

public class NetworkEntityRelRemainderCache extends Position {
	public NetworkEntityRelRemainderCache(int x, int y, int z) {
		super(x, y, z);
	}

	public NetworkEntityRelRemainderCache() {
		this(0, 0, 0);
	}
}
