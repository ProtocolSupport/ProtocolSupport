package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleResourcePackStatus extends ServerBoundMiddlePacket {

	public MiddleResourcePackStatus(ConnectionImpl connection) {
		super(connection);
	}

	protected int result;

	@Override
	public void writeToServer() {
		ServerBoundPacketData resourcepackstatus = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_RESOURCE_PACK_STATUS);
		VarNumberSerializer.writeVarInt(resourcepackstatus, result);
		codec.read(resourcepackstatus);
	}

}
