package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		NetworkEntityType type = entity.getType();
		if (type == NetworkEntityType.ARMOR_STAND_OBJECT) {
			return RecyclableEmptyList.get();
		}
		x *= 32;
		y *= 32;
		z *= 32;
		if (type == NetworkEntityType.ITEM_FRAME) {
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
		if (type == NetworkEntityType.FALLING_OBJECT) {
			int id = IdRemapper.BLOCK.getTable(version).getRemap((objectdata & 4095) << 4) >> 4;
			int data = (objectdata >> 12) & 0xF;
			objectdata = (id | (data << 16));
		}
		if ((type == NetworkEntityType.TNT) || (type == NetworkEntityType.FALLING_OBJECT)) {
			y += 16;
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, version);
		VarNumberSerializer.writeVarInt(serializer, entity.getId());
		serializer.writeByte(IdRemapper.ENTITY.getTable(version).getRemap(type).getTypeId());
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
