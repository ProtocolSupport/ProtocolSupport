package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEffectAdd extends MiddleEntityEffectAdd {

	public EntityEffectAdd(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_EFFECT_ADD);
		VarNumberSerializer.writeVarInt(serializer, entityId);
		serializer.writeByte(effectId);
		serializer.writeByte(amplifier);
		VarNumberSerializer.writeVarInt(serializer, duration);
		serializer.writeByte(flags);
		return RecyclableSingletonList.create(serializer);
	}

}
