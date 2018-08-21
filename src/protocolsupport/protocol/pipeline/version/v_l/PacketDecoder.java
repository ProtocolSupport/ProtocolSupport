package protocolsupport.protocol.pipeline.version.v_l;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_l.ClientLogin;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_l.Ping;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractLegacyPacketDecoder;

public class PacketDecoder extends AbstractLegacyPacketDecoder {

	{
		registry.register(NetworkState.HANDSHAKING, 0x02, ClientLogin::new);
		registry.register(NetworkState.HANDSHAKING, 0xFE, Ping::new);
	}

	public PacketDecoder(ConnectionImpl connection) {
		super(connection);
	}

}
