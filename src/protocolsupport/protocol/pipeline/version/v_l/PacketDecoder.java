package protocolsupport.protocol.pipeline.version.v_l;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.middle.base.serverbound.IServerboundMiddlePacket;
import protocolsupport.protocol.packet.middle.impl.serverbound.handshake.v_l.ClientLogin;
import protocolsupport.protocol.packet.middle.impl.serverbound.handshake.v_l.Ping;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractLegacyPacketDecoder;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;

public class PacketDecoder extends AbstractLegacyPacketDecoder<IServerboundMiddlePacket> {

	public PacketDecoder(IPacketDataChannelIO io, NetworkDataCache cache) {
		super(io, cache);
		registry.register(NetworkState.HANDSHAKING, 0x02, ClientLogin::new);
		registry.register(NetworkState.HANDSHAKING, 0xFE, Ping::new);
	}

}
