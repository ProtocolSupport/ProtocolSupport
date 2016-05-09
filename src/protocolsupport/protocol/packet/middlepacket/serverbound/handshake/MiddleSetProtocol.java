package protocolsupport.protocol.packet.middlepacket.serverbound.handshake;

import net.minecraft.server.v1_9_R1.Packet;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middlepacket.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middlepacketimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSetProtocol extends ServerBoundMiddlePacket {

	protected String hostname;
	protected int port;
	protected int nextState;

	@Override
	public RecyclableCollection<? extends Packet<?>> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.HANDSHAKE_START.get());
		creator.writeVarInt(ProtocolVersion.getLatest().getId());
		creator.writeString(hostname);
		creator.writeShort(port);
		creator.writeVarInt(nextState);
		return RecyclableSingletonList.create(creator.create());
	}

}
