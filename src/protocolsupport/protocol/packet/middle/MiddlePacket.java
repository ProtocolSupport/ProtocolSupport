package protocolsupport.protocol.packet.middle;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.IPacketDataIO;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.utils.reflection.ReflectionUtils;

public abstract class MiddlePacket implements IMiddlePacket {

	protected final IPacketDataIO io;
	protected final NetworkDataCache cache;
	protected final ProtocolVersion version;

	protected MiddlePacket(IMiddlePacketInit init) {
		this.io = init.getIO();
		this.cache = init.getCache();
		this.version = io.getVersion();
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

	public static interface IMiddlePacketInit {

		public IPacketDataIO getIO();

		public NetworkDataCache getCache();

	}

}
