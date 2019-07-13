package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject {

	public SpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntityType type = entityRemapper.getRemappedEntityType();
		objectdata = entityObjectDataRemappingTable.getRemap(type).applyAsInt(objectdata);
		x *= 32;
		y *= 32;
		z *= 32;
		switch (type) {
			case ITEM_FRAME: {
				switch (objectdata) {
					case 0: {
						z -= 32;
						yaw = (byte) 128;
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
						yaw = (byte) 192;
						break;
					}
				}
				break;
			}
			case TNT:
			case MINECART:
			case MINECART_CHEST:
			case MINECART_FURNACE:
			case MINECART_TNT:
			case MINECART_MOB_SPAWNER:
			case MINECART_HOPPER:
			case MINECART_COMMAND:
			case FALLING_OBJECT: {
				y += 16;
				break;
			}
			default: {
				break;
			}
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SPAWN_OBJECT_ID);
		VarNumberSerializer.writeVarInt(serializer, entity.getId());
		serializer.writeByte(LegacyEntityId.getObjectIntId(type));
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
