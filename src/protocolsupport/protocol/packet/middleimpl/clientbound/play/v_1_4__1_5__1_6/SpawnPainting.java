package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnPainting extends MiddleSpawnPainting<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
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
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, version);
		serializer.writeInt(entityId);
		serializer.writeString(type);
		serializer.writeLegacyPositionI(position);
		serializer.writeInt(direction);
		return RecyclableSingletonList.create(serializer);
	}

}
