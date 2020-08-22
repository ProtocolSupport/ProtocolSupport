package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLook;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityLook extends MiddleEntityLook {

	public EntityLook(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData entitylook = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_LOOK);
		entitylook.writeInt(entityId);
		entitylook.writeByte(yaw);
		entitylook.writeByte(pitch);
		codec.write(entitylook);
	}

}
