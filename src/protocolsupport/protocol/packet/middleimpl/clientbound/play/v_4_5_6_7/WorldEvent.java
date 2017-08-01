package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldEvent extends MiddleWorldEvent {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		effectId = IdRemapper.EFFECT.getTable(version).getRemap(effectId);
		effectId = LegacyEffect.getLegacyId(version, effectId);
		if (effectId == 2001) {
			data = IdRemapper.BLOCK.getTable(version).getRemap((data & 0xFFF) << 4) >> 4;
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_EVENT_ID, version);
		serializer.writeInt(effectId);
		PositionSerializer.writeLegacyPositionB(serializer, position);
		serializer.writeInt(data);
		serializer.writeBoolean(disableRelative);
		return RecyclableSingletonList.create(serializer);
	}

}
