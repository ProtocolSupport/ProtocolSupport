package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleQueryEntityNBT extends ServerBoundMiddlePacket {

	protected MiddleQueryEntityNBT(IMiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected int entityId;

	@Override
	protected void write() {
		ServerBoundPacketData queryentitynbt = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_QUERY_ENTITY_NBT);
		VarNumberCodec.writeVarInt(queryentitynbt, id);
		VarNumberCodec.writeVarInt(queryentitynbt, entityId);
		io.writeServerbound(queryentitynbt);
	}

}
