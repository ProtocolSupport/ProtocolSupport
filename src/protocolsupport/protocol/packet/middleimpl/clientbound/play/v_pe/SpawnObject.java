package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class SpawnObject extends MiddleSpawnObject {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		switch (entity.getType()) {
			case ITEM: {
				//We need to prepare the item because we can only spawn it after we've received the first metadata update.
				cache.prepareItem(new PreparedItem(entity.getId(), x, y, z, motX / 8.000F, motY / 8000.F, motZ / 8000.F));
				return RecyclableEmptyList.get();
			}
			case ITEM_FRAME: {
				//Still not here :/
				return RecyclableEmptyList.get();
			}
			default: {
				return RecyclableSingletonList.create(SpawnLiving.create(
						version,
						entity,
						x, y, z,
						motX / 8.000F, motY / 8000.F, motZ / 8000.F,
						pitch, yaw, cache.getLocale(),
						null, //TODO: Add spawnmeta to something like sand.
						PEDataValues.getObjectEntityTypeId(IdRemapper.ENTITY.getTable(version).getRemap(entity.getType()))
					));
			}
		}
	}

	public class PreparedItem {

		private final int entityId;
		private final double x;
		private final double y;
		private final double z;
		private final float motX;
		private final float motY;
		private final float motZ;
		private ItemStackWrapper itemstack;
		private boolean spawned = false;

		PreparedItem(int entityId, double x, double y, double z, float motX, float motY, float motZ) {
			this.entityId = entityId;
			this.x = x;
			this.y = y;
			this.z = z;
			this.motX = motX;
			this.motY = motY;
			this.motZ = motZ;
		}

		public int getId() {
			return entityId;
		}

		public RecyclableArrayList<ClientBoundPacketData> updateItem(ProtocolVersion version, ItemStackWrapper itemstack) {
			RecyclableArrayList<ClientBoundPacketData> updatepackets = RecyclableArrayList.create();
			if ((this.itemstack == null) || !this.itemstack.equals(itemstack)) {
				if (spawned) {
					updatepackets.add(EntityDestroy.create(version, entityId));
					spawned = false;
				}
				this.itemstack = itemstack;
			}
			if (!spawned) {
				ClientBoundPacketData spawn = ClientBoundPacketData.create(PEPacketIDs.ADD_ITEM_ENTITY, version);
				VarNumberSerializer.writeSVarLong(spawn, entityId);
				VarNumberSerializer.writeVarLong(spawn, entityId);
				ItemStackSerializer.writeItemStack(spawn, version, cache.getLocale(), itemstack, true);
				spawn.writeFloatLE((float) x);
				spawn.writeFloatLE((float) y);
				spawn.writeFloatLE((float) z);
				spawn.writeFloatLE(motX / 8000);
				spawn.writeFloatLE(motY / 8000);
				spawn.writeFloatLE(motZ / 8000);
				VarNumberSerializer.writeVarInt(spawn, 0); //Metadata?
				updatepackets.add(spawn);
				spawned = true;
			}
			return updatepackets;
		}

	}

}
