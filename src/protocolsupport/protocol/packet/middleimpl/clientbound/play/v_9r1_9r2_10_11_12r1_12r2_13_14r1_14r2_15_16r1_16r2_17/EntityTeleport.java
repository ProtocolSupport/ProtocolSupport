package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17.AbstractLocationOffsetEntityTeleport;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EntityTeleport extends AbstractLocationOffsetEntityTeleport {

	public EntityTeleport(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entityteleport = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_TELEPORT);
		VarNumberSerializer.writeVarInt(entityteleport, entityId);
		entityteleport.writeDouble(x);
		entityteleport.writeDouble(y);
		entityteleport.writeDouble(z);
		entityteleport.writeByte(yaw);
		entityteleport.writeByte(pitch);
		entityteleport.writeBoolean(onGround);
		codec.writeClientbound(entityteleport);
	}

}
