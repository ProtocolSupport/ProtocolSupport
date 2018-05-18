package protocolsupport.protocol.utils.types.networkentity;

import protocolsupport.protocol.utils.types.Position;

/**
 * Created by JunHyeong Lim on 2018-05-18
 */
public class NetworkEntityRelRemainderCache extends Position {
	public NetworkEntityRelRemainderCache(int x, int y, int z) {
		super(x, y, z);
	}

	public NetworkEntityRelRemainderCache() {
		this(0, 0, 0);
	}
}
