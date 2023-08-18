package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__7;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__8.AbstractLegacyPotionItemEntityVelocity;

public class EntityVelocity extends AbstractLegacyPotionItemEntityVelocity implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public EntityVelocity(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(create(entity.getId(), velX, velY, velZ));
	}

	public static ClientBoundPacketData create(int entityId, short velX, short velY, short velZ) {
		ClientBoundPacketData entityvelocity = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_VELOCITY);
		entityvelocity.writeInt(entityId);
		entityvelocity.writeShort(velX);
		entityvelocity.writeShort(velY);
		entityvelocity.writeShort(velZ);
		return entityvelocity;
	}

}
