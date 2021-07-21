package protocolsupport.protocol.packet.middle;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PacketDataCodec;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.utils.ReflectionUtils;

public abstract class MiddlePacket {

	protected final PacketDataCodec codec;
	protected final NetworkDataCache cache;
	protected final ProtocolVersion version;

	protected MiddlePacket(MiddlePacketInit init) {
		this.codec = init.getCodec();
		this.cache = init.getCache();
		this.version = init.getVersion();
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

	public static interface MiddlePacketInit {

		public PacketDataCodec getCodec();

		public ProtocolVersion getVersion();

		public NetworkDataCache getCache();

	}

}
