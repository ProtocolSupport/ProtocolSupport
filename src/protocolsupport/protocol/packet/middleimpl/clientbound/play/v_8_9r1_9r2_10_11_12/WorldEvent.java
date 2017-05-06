package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyEffect;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldEvent extends MiddleWorldEvent {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		effectId = IdRemapper.EFFECT.getTable(version).getRemap(effectId);
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9)) {
			effectId = LegacyEffect.getLegacyId(version, effectId);
			if (effectId == 2001) {
				data = IdRemapper.BLOCK.getTable(version).getRemap((data & 0xFFF) << 4) >> 4;
			}
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_EVENT_ID, version);
		serializer.writeInt(effectId);
		PositionSerializer.writePosition(serializer, position);
		serializer.writeInt(data);
		serializer.writeBoolean(disableRelative);
		return RecyclableSingletonList.create(serializer);
	}

}
