package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17.AbstractLocationOffsetEntityLook;

public class EntityLook extends AbstractLocationOffsetEntityLook {

	public EntityLook(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entitylook = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_LOOK);
		VarNumberCodec.writeVarInt(entitylook, entity.getId());
		entitylook.writeByte(yaw);
		entitylook.writeByte(pitch);
		entitylook.writeBoolean(onGround);
		codec.writeClientbound(entitylook);
	}

}
