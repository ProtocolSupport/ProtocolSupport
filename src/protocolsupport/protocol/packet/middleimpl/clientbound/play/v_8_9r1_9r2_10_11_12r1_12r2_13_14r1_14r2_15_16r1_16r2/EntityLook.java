package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLook;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityLook extends MiddleEntityLook {

	public EntityLook(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData entitylook = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_LOOK);
		VarNumberSerializer.writeVarInt(entitylook, entityId);
		entitylook.writeByte(yaw);
		entitylook.writeByte(pitch);
		entitylook.writeBoolean(onGround);
		codec.write(entitylook);
	}

}
