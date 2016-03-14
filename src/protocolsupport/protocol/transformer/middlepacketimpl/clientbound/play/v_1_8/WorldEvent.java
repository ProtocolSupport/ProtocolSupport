package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleWorldEvent;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldEvent extends MiddleWorldEvent<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (effectId == 2001) {
			data = IdRemapper.BLOCK.getTable(version).getRemap((data & 0xFFF) << 4) >> 4;
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WORLD_EVENT_ID, version);
		serializer.writeInt(effectId);
		serializer.writePosition(position);
		serializer.writeInt(data);
		serializer.writeBoolean(disableRelative);
		return RecyclableSingletonList.create(serializer);
	}

}
