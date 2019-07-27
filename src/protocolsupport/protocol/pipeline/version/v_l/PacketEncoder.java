package protocolsupport.protocol.pipeline.version.v_l;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_l.LoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.noop.NoopPong;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_l.ServerInfo;
import protocolsupport.protocol.pipeline.version.util.encoder.AbstractPacketEncoder;

public class PacketEncoder extends AbstractPacketEncoder {

	public PacketEncoder(ConnectionImpl connection) {
		super(connection);
	}

	{
		registry.register(NetworkState.LOGIN, PacketType.CLIENTBOUND_LOGIN_DISCONNECT, LoginDisconnect::new);
		registry.register(NetworkState.STATUS, PacketType.CLIENTBOUND_STATUS_SERVER_INFO, ServerInfo::new);
		registry.register(NetworkState.STATUS, PacketType.CLIENTBOUND_STATUS_PONG, NoopPong::new);
	}

}
