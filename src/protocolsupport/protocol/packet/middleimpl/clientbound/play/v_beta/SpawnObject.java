package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.networkentity.NetworkEntityItemDataCache;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject {

	public SpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (entity.getType() == NetworkEntityType.ITEM_FRAME) {
			//TODO: spawn displayed item instead?
			return RecyclableEmptyList.get();
		}
		if (entity.getType() == NetworkEntityType.ITEM) {
			((NetworkEntityItemDataCache) entity.getDataCache()).setSpawnCoords(x, y, z);
			return RecyclableEmptyList.get();
		}
		NetworkEntityType type = entityRemapper.getRemappedEntityType();
		objectdata = entityObjectDataRemappingTable.getRemap(type).applyAsInt(objectdata);
		x *= 32;
		y *= 32;
		z *= 32;
		switch (type) {
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
		serializer.writeInt(entity.getId());
		serializer.writeByte(type.getNetworkTypeId());
		serializer.writeInt((int) x);
		serializer.writeInt((int) y);
		serializer.writeInt((int) z);
		serializer.writeInt(objectdata);
		if (objectdata > 0) {
			serializer.writeShort(motX);
			serializer.writeShort(motY);
			serializer.writeShort(motZ);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
