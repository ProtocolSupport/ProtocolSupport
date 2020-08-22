package protocolsupport.protocol.pipeline.version.util;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketDataCodec;
import protocolsupport.protocol.packet.middle.MiddlePacket.MiddlePacketInit;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public class ConnectionImplMiddlePacketInit implements MiddlePacketInit {

	private final ConnectionImpl connection;

	public ConnectionImplMiddlePacketInit(ConnectionImpl connection) {
		this.connection = connection;
	}

	@Override
	public PacketDataCodec getCodec() {
		return connection.getCodec();
	}

	@Override
	public ProtocolVersion getVersion() {
		return connection.getVersion();
	}

	@Override
	public NetworkDataCache getCache() {
		return connection.getCache();
	}

}
