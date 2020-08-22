package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleQueryEntityNBT extends ServerBoundMiddlePacket {

	public MiddleQueryEntityNBT(MiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected int entityId;

	@Override
	protected void writeToServer() {
		ServerBoundPacketData queryentitynbt = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_QUERY_ENTITY_NBT);
		VarNumberSerializer.writeVarInt(queryentitynbt, id);
		VarNumberSerializer.writeVarInt(queryentitynbt, entityId);
		codec.read(queryentitynbt);
	}

}
