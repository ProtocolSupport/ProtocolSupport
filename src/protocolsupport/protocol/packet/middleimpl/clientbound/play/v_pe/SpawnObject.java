package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class SpawnObject extends MiddleSpawnObject {
	
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if(entity.getType() == NetworkEntityType.ITEM) {
			//We need to prepare the item because we can only spawn it after we've received the first metadata update.
			cache.prepareItem(new PreparedItem(entity.getId(), x, y, z, motX / 8.000F, motY / 8000.F, motZ / 8000.F));
			return RecyclableEmptyList.get();
		} else { 
			return RecyclableSingletonList.create(SpawnLiving.create(
				version,
				entity.getId(), 
				x, y, z,
				motX / 8.000F, motY / 8000.F, motZ / 8000.F, 
				pitch, yaw,
				null, 
				PEDataValues.getObjectEntityTypeId(IdRemapper.ENTITY.getTable(version).getRemap(entity.getType()))
			));
		}
	}
	
	public class PreparedItem {
		
		private int entityId;
		private double x; 
		private double y;
		private double z;
		private float motX;
		private float motY;
		private float motZ;
		private ItemStackWrapper itemstack;
		
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
		
		public void setItemStack(ItemStackWrapper itemstack) {
			this.itemstack = itemstack;
		}
		
		public ClientBoundPacketData getSpawnPacket(ProtocolVersion version) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ADD_ITEM_ENTITY, version);
			VarNumberSerializer.writeSVarLong(serializer, entityId);
			VarNumberSerializer.writeVarLong(serializer, entityId);
			ItemStackSerializer.writePeSlot(serializer, version, itemstack);
			MiscSerializer.writeLFloat(serializer, (float) x);
			MiscSerializer.writeLFloat(serializer, (float) y);
			MiscSerializer.writeLFloat(serializer, (float) z);
			MiscSerializer.writeLFloat(serializer, motX); 
			MiscSerializer.writeLFloat(serializer, motY);
			MiscSerializer.writeLFloat(serializer, motZ);
			VarNumberSerializer.writeVarInt(serializer, 0);
			return serializer;
		}
	}

}
