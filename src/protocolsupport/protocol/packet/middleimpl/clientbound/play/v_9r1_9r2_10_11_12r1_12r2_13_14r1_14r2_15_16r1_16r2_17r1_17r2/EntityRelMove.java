package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityRelMove extends MiddleEntityRelMove {

	public EntityRelMove(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entityrelmove = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_REL_MOVE);
		VarNumberCodec.writeVarInt(entityrelmove, entity.getId());
		entityrelmove.writeShort(relX);
		entityrelmove.writeShort(relY);
		entityrelmove.writeShort(relZ);
		entityrelmove.writeBoolean(onGround);
		codec.writeClientbound(entityrelmove);
	}

}
