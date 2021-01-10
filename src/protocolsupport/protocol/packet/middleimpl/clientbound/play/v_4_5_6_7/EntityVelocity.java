package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractPotionItemAsObjectDataEntityVelocity;

public class EntityVelocity extends AbstractPotionItemAsObjectDataEntityVelocity {

	public EntityVelocity(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		codec.writeClientbound(create(entityId, velX, velY, velZ));
	}

	public static ClientBoundPacketData create(int entityId, short velX, short velY, short velZ) {
		ClientBoundPacketData entityvelocity = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_VELOCITY);
		entityvelocity.writeInt(entityId);
		entityvelocity.writeShort(velX);
		entityvelocity.writeShort(velY);
		entityvelocity.writeShort(velZ);
		return entityvelocity;
	}

}
