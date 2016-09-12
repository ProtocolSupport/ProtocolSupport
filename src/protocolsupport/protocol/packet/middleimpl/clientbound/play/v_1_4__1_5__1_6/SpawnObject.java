package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		if (type == 78) { //skip armor stands
			return RecyclableEmptyList.get();
		}
		x *= 32;
		y *= 32;
		z *= 32;
		if (type == 71) {
			switch (objectdata) {
				case 0: {
					z -= 32;
					yaw = 128;
					break;
				}
				case 1: {
					x += 32;
					yaw = 64;
					break;
				}
				case 2: {
					z += 32;
					yaw = 0;
					break;
				}
				case 3: {
					x -= 32;
					yaw = 192;
					break;
				}
			}
		}
		if (type == 70) {
			int id = IdRemapper.BLOCK.getTable(version).getRemap((objectdata & 4095) << 4) >> 4;
			int data = (objectdata >> 12) & 0xF;
			objectdata = (id | (data << 16));
		}
		if ((type == 50) || (type == 70) || (type == 74)) {
			y += 16;
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, version);
		serializer.writeInt(entityId);
		serializer.writeByte(IdRemapper.ENTITY_OBJECT.getTable(version).getRemap(type));
		serializer.writeInt((int) x);
		serializer.writeInt((int) y);
		serializer.writeInt((int) z);
		serializer.writeByte(pitch);
		serializer.writeByte(yaw);
		serializer.writeInt(objectdata);
		if (objectdata > 0) {
			serializer.writeShort(motX);
			serializer.writeShort(motY);
			serializer.writeShort(motZ);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
