package protocolsupport.protocol.transformer.middlepacket.serverbound.handshake;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.Packet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ServerBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketCreator;

public abstract class MiddleSetProtocol extends ServerBoundMiddlePacket {

	protected String hostname;
	protected int port;
	protected int nextState;

	@Override
	public Collection<Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.HANDSHAKE_START.get());
		creator.writeVarInt(ProtocolVersion.getLatest().getId());
		creator.writeString(hostname);
		creator.writeShort(port);
		creator.writeVarInt(nextState);
		return Collections.<Packet<?>>singletonList(creator.create());
	}

}
