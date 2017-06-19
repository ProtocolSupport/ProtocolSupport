package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEffectAdd extends MiddleEntityEffectAdd {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EFFECT, version);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		serializer.writeByte(1); // Add effect
		VarNumberSerializer.writeSVarInt(serializer, effectId);
		VarNumberSerializer.writeSVarInt(serializer, amplifier);
		serializer.writeBoolean(!hideParticles);
		VarNumberSerializer.writeSVarInt(serializer, duration);
		return RecyclableSingletonList.create(serializer);
	}

}
