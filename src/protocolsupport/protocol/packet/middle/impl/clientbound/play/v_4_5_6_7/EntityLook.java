package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2.AbstractLocationOffsetEntityLook;

public class EntityLook extends AbstractLocationOffsetEntityLook implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public EntityLook(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entitylook = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_LOOK);
		entitylook.writeInt(entity.getId());
		entitylook.writeByte(yaw);
		entitylook.writeByte(pitch);
		io.writeClientbound(entitylook);
	}

}
