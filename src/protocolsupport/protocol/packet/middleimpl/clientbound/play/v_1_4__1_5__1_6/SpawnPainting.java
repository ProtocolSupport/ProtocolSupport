package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnPainting extends MiddleSpawnPainting<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		switch (direction) {
			case 0: {
				position.modifyZ(-1);
				break;
			}
			case 1: {
				position.modifyX(1);
				break;
			}
			case 2: {
				position.modifyZ(1);
				break;
			}
			case 3: {
				position.modifyX(-1);
				break;
			}
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, version);
		serializer.writeInt(entityId);
		serializer.writeString(type);
		serializer.writeLegacyPositionI(position);
		return RecyclableSingletonList.create(serializer);
	}

}
