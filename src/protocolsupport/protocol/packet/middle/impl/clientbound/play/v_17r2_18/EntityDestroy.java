package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r2_18;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;

public class EntityDestroy extends MiddleEntityDestroy implements
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public EntityDestroy(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entitydestroyPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_DESTROY);
		ArrayCodec.writeVarIntTArray(entitydestroyPacket, entities, (entityTo, entity) -> VarNumberCodec.writeVarInt(entityTo, entity.getId()));
		io.writeClientbound(entitydestroyPacket);
	}

}
