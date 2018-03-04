package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEInventory.PESource;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class EntityEquipment extends MiddleEntityEquipment {
	
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		String locale = cache.getAttributesCache().getLocale();
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity == null) {
			return RecyclableEmptyList.get();
		}
		DataCache dataCache = entity.getDataCache();
		if (slot > 1) {
			// Armor update
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOB_ARMOR_EQUIPMENT, version);
			VarNumberSerializer.writeVarLong(serializer, entityId);
			switch (slot) {
				case (2): {
					dataCache.setBoots(itemstack);
					break;
				}
				case (3): {
					dataCache.setLeggings(itemstack);
					break;
				}
				case (4): {
					dataCache.setChestplate(itemstack);
					break;
				}
				case (5): {
					dataCache.setHelmet(itemstack);
					break;
				}
			}
			ItemStackSerializer.writeItemStack(serializer, version, locale, dataCache.getHelmet(), true);
			ItemStackSerializer.writeItemStack(serializer, version, locale, dataCache.getChestplate(), true);
			ItemStackSerializer.writeItemStack(serializer, version, locale, dataCache.getLeggings(), true);
			ItemStackSerializer.writeItemStack(serializer, version, locale, dataCache.getBoots(), true);
			return RecyclableSingletonList.create(serializer);
		}
		if (slot == 1) {
			dataCache.setHand(itemstack);
		} else {
			dataCache.setOffHand(itemstack);
		}
		return RecyclableSingletonList.create(createUpdateHand(version, locale, entityId, itemstack, cache.getWatchedEntityCache().isSelf(entityId) ? cache.getPEInventoryCache().getSelectedSlot() : 0, slot == 1));
	}
	
	public static ClientBoundPacketData createUpdateHand(ProtocolVersion version, String locale, int entityId, ItemStackWrapper itemstack, int slot, boolean isMainHand) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOB_EQUIPMENT, version);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		ItemStackSerializer.writeItemStack(serializer, version, locale, itemstack, true);
		serializer.writeByte(slot);
		serializer.writeByte(slot);
		serializer.writeByte(isMainHand ? PESource.POCKET_OFFHAND : PESource.POCKET_INVENTORY);
		return serializer;
	}

}
