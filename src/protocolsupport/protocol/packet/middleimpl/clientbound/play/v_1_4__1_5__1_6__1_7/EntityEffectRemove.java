package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEffectRemove;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEffectRemove extends MiddleEntityEffectRemove<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		if (IdSkipper.EFFECT.getTable(version).shouldSkip(effectId)) {
			return RecyclableEmptyList.get();
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, version);
		serializer.writeInt(entityId);
		serializer.writeByte(effectId);
		return RecyclableSingletonList.create(serializer);
	}

}
