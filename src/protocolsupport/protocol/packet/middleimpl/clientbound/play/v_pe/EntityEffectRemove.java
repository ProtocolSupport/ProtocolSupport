package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectRemove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEffectRemove extends MiddleEntityEffectRemove {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EFFECT, version);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		serializer.writeByte(3); // Remove effect
		VarNumberSerializer.writeSVarInt(serializer, effectId);
		VarNumberSerializer.writeSVarInt(serializer, 0); // unused
		serializer.writeBoolean(false); // unused
		VarNumberSerializer.writeSVarInt(serializer, 0); // unused
		return RecyclableSingletonList.create(serializer);
	}

}
