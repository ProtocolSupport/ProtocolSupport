package protocolsupport.protocol.pipeline.version.v_l;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.IClientboundMiddlePacket;
import protocolsupport.protocol.packet.middle.impl.clientbound.login.v_l.LoginDisconnect;
import protocolsupport.protocol.packet.middle.impl.clientbound.status.noop.NoopPong;
import protocolsupport.protocol.packet.middle.impl.clientbound.status.v_l.ServerInfo;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.version.util.encoder.AbstractPacketEncoder;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public class PacketEncoder extends AbstractPacketEncoder<IClientboundMiddlePacket> {

	public PacketEncoder(IPacketDataChannelIO io, NetworkDataCache cache) {
		super(io, cache);
		registry.register(NetworkState.LOGIN, ClientBoundPacketType.LOGIN_DISCONNECT, LoginDisconnect::new);
		registry.register(NetworkState.STATUS, ClientBoundPacketType.STATUS_SERVER_INFO, ServerInfo::new);
		registry.register(NetworkState.STATUS, ClientBoundPacketType.STATUS_PONG, NoopPong::new);
	}

}
