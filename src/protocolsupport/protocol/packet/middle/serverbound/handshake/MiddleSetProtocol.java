package protocolsupport.protocol.packet.middle.serverbound.handshake;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSetProtocol extends ServerBoundMiddlePacket {

	protected String hostname;
	protected int port;
	protected int nextState;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.HANDSHAKE_START);
		VarNumberSerializer.writeVarInt(creator, ProtocolVersion.getLatest().getId());
		StringSerializer.writeString(creator, ProtocolVersion.getLatest(), hostname);
		creator.writeShort(port);
		VarNumberSerializer.writeVarInt(creator, nextState);
		return RecyclableSingletonList.create(creator);
	}

}
