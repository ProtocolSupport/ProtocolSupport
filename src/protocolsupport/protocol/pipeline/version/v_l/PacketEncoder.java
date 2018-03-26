package protocolsupport.protocol.pipeline.version.v_l;

import protocolsupport.api.Connection;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_l.LoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.noop.NoopPong;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_l.ServerInfo;
import protocolsupport.protocol.pipeline.version.util.encoder.AbstractLegacyPacketEncoder;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.utils.registry.PacketIdTransformerRegistry;

public class PacketEncoder extends AbstractLegacyPacketEncoder {

	protected static final PacketIdTransformerRegistry packetIdRegistry = new PacketIdTransformerRegistry();
	static {
		packetIdRegistry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, 0xFF);
		packetIdRegistry.register(NetworkState.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, 0xFF);
	}

	@Override
	protected int getNewPacketId(NetworkState currentProtocol, int oldPacketId) {
		return packetIdRegistry.getNewPacketId(currentProtocol, oldPacketId);
	}

	{
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, LoginDisconnect.class);
		registry.register(NetworkState.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, ServerInfo.class);
		registry.register(NetworkState.STATUS, ClientBoundPacket.STATUS_PONG_ID, NoopPong.class);
	}

	public PacketEncoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

}
