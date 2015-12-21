package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityAttach;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityAttach extends MiddleEntityAttach<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (leash) {
			return RecyclableEmptyList.get();
		} else {
			PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
			serializer.writeInt(entityId);
			serializer.writeInt(vehicleId);
			return RecyclableSingletonList.<PacketData>create(PacketData.create(ClientBoundPacket.PLAY_ENTITY_ATTACH_ID, serializer));
		}
	}

}
