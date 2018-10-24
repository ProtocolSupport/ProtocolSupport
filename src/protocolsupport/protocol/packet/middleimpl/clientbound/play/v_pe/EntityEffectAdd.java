package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEffectAdd extends MiddleEntityEffectAdd {

	public EntityEffectAdd(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EFFECT);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		serializer.writeByte(1); // Add effect
		VarNumberSerializer.writeSVarInt(serializer, effectId);
		VarNumberSerializer.writeSVarInt(serializer, amplifier);
		serializer.writeBoolean((flags & 0x02) != 0);
		VarNumberSerializer.writeSVarInt(serializer, duration);
		return RecyclableSingletonList.create(serializer);
	}

}
