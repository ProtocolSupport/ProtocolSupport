package protocolsupport.protocol.pipeline.version.util;

import protocolsupport.protocol.packet.middle.MiddlePacket.IMiddlePacketInit;
import protocolsupport.protocol.pipeline.IPacketDataIO;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public class MiddlePacketInitImpl implements IMiddlePacketInit {

	private final IPacketDataIO io;
	private final NetworkDataCache cache;

	public MiddlePacketInitImpl(IPacketDataIO io, NetworkDataCache cache) {
		this.io = io;
		this.cache = cache;
	}

	@Override
	public IPacketDataIO getIO() {
		return io;
	}

	@Override
	public NetworkDataCache getCache() {
		return cache;
	}

}
