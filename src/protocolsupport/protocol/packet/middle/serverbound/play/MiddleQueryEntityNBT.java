package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleQueryEntityNBT extends ServerBoundMiddlePacket {

	protected MiddleQueryEntityNBT(MiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected int entityId;

	@Override
	protected void write() {
		ServerBoundPacketData queryentitynbt = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_QUERY_ENTITY_NBT);
		VarNumberSerializer.writeVarInt(queryentitynbt, id);
		VarNumberSerializer.writeVarInt(queryentitynbt, entityId);
		codec.writeServerbound(queryentitynbt);
	}

}
