package protocolsupport.protocol.packet.middle;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketDataCodec;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.utils.Utils;

public abstract class MiddlePacket {

	protected final ConnectionImpl connection;
	protected final PacketDataCodec codec;
	protected final NetworkDataCache cache;
	protected final ProtocolVersion version;
	public MiddlePacket(ConnectionImpl connection) {
		this.connection = connection;
		this.codec = connection.getCodec();
		this.cache = connection.getCache();
		this.version = connection.getVersion();
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
