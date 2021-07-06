package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityDestroy extends MiddleEntityDestroy {

	public EntityDestroy(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entitydestroyPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_DESTROY);
		ArrayCodec.writeVarIntTArray(entitydestroyPacket, entities, (entityTo, entity) -> VarNumberCodec.writeVarInt(entityTo, entity.getId()));
		codec.writeClientbound(entitydestroyPacket);
	}

}
