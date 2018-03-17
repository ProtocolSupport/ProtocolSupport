package protocolsupport.protocol.storage.netcache;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityDestroy;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

//TODO: move to network entity data cache
public class PEItemEntityCache {

	protected final Int2ObjectOpenHashMap<ItemEntityInfo> preparedItems = new Int2ObjectOpenHashMap<>();

	public void removeItem(int entityId) {
		preparedItems.remove(entityId);
	}

	public void addItem(ItemEntityInfo preparedItem) {
		preparedItems.put(preparedItem.getId(), preparedItem);
	}

	public ItemEntityInfo getItem(int entityId) {
		return preparedItems.get(entityId);
	}

	public static class ItemEntityInfo {

		protected final int entityId;
		protected final double x;
		protected final double y;
		protected final double z;
		protected final float motX;
		protected final float motY;
		protected final float motZ;
		protected ItemStackWrapper itemstack;
		protected boolean spawned = false;

		public ItemEntityInfo(int entityId, double x, double y, double z, float motX, float motY, float motZ) {
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
				ItemStackSerializer.writeItemStack(spawn, version, I18NData.DEFAULT_LOCALE, itemstack, true);
				spawn.writeFloatLE((float) x);
				spawn.writeFloatLE((float) y);
				spawn.writeFloatLE((float) z);
				spawn.writeFloatLE(motX / 8000);
				spawn.writeFloatLE(motY / 8000);
				spawn.writeFloatLE(motZ / 8000);
				VarNumberSerializer.writeVarInt(spawn, 0); // Metadata?
				updatepackets.add(spawn);
				spawned = true;
			}
			return updatepackets;
		}

	}

}
