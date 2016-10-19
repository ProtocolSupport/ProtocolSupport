package protocolsupport.protocol.packet.middle.serverbound.handshake;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSetProtocol extends ServerBoundMiddlePacket {

	protected String hostname;
	protected int port;
	protected int nextState;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() throws Exception {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.HANDSHAKE_START);
		creator.writeVarInt(ProtocolVersion.getLatest().getId());
		creator.writeString(hostname);
		creator.writeShort(port);
		creator.writeVarInt(nextState);
		return RecyclableSingletonList.create(creator);
	}

}
