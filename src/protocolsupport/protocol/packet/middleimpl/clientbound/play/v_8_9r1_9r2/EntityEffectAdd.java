package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEffectAdd extends MiddleEntityEffectAdd {

	public EntityEffectAdd(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID);
		VarNumberSerializer.writeVarInt(serializer, entityId);
		serializer.writeByte(effectId);
		serializer.writeByte(amplifier);
		VarNumberSerializer.writeVarInt(serializer, duration);
		serializer.writeBoolean((flags & 0x01) != 0);
		return RecyclableSingletonList.create(serializer);
	}

}
