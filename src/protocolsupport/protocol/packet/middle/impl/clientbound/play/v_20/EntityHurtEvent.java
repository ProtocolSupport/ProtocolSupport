package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleEntityHurtEvent;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class EntityHurtEvent extends MiddleEntityHurtEvent
implements IClientboundMiddlePacketV20 {

	public EntityHurtEvent(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData entitydamagePacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ENTITY_HURT_EVENT);
		VarNumberCodec.writeVarInt(entitydamagePacket, entity.getId());
		VarNumberCodec.writeVarInt(entitydamagePacket, damageTypeId);
		VarNumberCodec.writeVarInt(entitydamagePacket, causeEntityId);
		VarNumberCodec.writeVarInt(entitydamagePacket, damagerEntityId);
		OptionalCodec.writeOptional(entitydamagePacket, location, (locationData, locationElement) -> {
			locationData.writeDouble(locationElement.getX());
			locationData.writeDouble(locationElement.getY());
			locationData.writeDouble(locationElement.getZ());
		});
		io.writeClientbound(entitydamagePacket);
	}

}
