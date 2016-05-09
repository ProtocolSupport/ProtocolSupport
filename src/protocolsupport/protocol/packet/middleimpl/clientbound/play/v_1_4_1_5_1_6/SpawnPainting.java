package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4_1_5_1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnPainting;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnPainting extends MiddleSpawnPainting<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		int x = position.getX();
		int z = position.getZ();
		switch (direction) {
			case 0: {
				--z;
				break;
			}
			case 1: {
				++x;
				break;
			}
			case 2: {
				++z;
				break;
			}
			case 3: {
				--x;
				break;
			}
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, version);
		serializer.writeInt(entityId);
		serializer.writeString(type);
		serializer.writeInt(x);
		serializer.writeInt(position.getY());
		serializer.writeInt(z);
		serializer.writeInt(direction);
		return RecyclableSingletonList.create(serializer);
	}

}
