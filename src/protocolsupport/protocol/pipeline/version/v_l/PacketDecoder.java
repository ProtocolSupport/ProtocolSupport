package protocolsupport.protocol.pipeline.version.v_l;

import protocolsupport.api.Connection;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_l.ClientLogin;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_l.Ping;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractLegacyPacketDecoder;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public class PacketDecoder extends AbstractLegacyPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x02, ClientLogin.class);
		registry.register(NetworkState.HANDSHAKING, 0xFE, Ping.class);
	}

	public PacketDecoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

}
