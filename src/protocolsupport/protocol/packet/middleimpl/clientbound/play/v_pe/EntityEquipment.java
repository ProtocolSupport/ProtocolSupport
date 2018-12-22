package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory.PESource;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEquipment extends MiddleEntityEquipment {

	public EntityEquipment(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		String locale = cache.getAttributesCache().getLocale();
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity == null) {
			return RecyclableEmptyList.get();
		}
		NetworkEntityDataCache dataCache = entity.getDataCache();
		if (slot > 1) {
			// Armor update
			switch (slot) {
				case 2: {
					dataCache.setBoots(itemstack);
					break;
				}
				case 3: {
					dataCache.setLeggings(itemstack);
					break;
				}
				case 4: {
					dataCache.setChestplate(itemstack);
					break;
				}
				case 5: {
					dataCache.setHelmet(itemstack);
					break;
				}
			}
			return RecyclableSingletonList.create(create(version, locale, entityId, dataCache.getHelmet(), dataCache.getChestplate(), dataCache.getLeggings(), dataCache.getBoots()));
		}
		if (slot == 1) {
			dataCache.setHand(itemstack);
		} else {
			dataCache.setOffHand(itemstack);
		}
		return RecyclableSingletonList.create(createUpdateHand(version, locale, entityId, itemstack, cache.getWatchedEntityCache().isSelf(entityId) ? cache.getPEInventoryCache().getSelectedSlot() : 0, slot == 1));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, long entityId, NetworkItemStack helmet, NetworkItemStack chestplate, NetworkItemStack leggings, NetworkItemStack boots) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOB_ARMOR_EQUIPMENT);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		ItemStackSerializer.writeItemStack(serializer, version, locale, helmet, true);
		ItemStackSerializer.writeItemStack(serializer, version, locale, chestplate, true);
		ItemStackSerializer.writeItemStack(serializer, version, locale, leggings, true);
		ItemStackSerializer.writeItemStack(serializer, version, locale, boots, true);
		return serializer;
	}

	public static ClientBoundPacketData createUpdateHand(ProtocolVersion version, String locale, int entityId, NetworkItemStack itemstack, int slot, boolean isMainHand) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOB_EQUIPMENT);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		ItemStackSerializer.writeItemStack(serializer, version, locale, itemstack, true);
		serializer.writeByte(slot);
		serializer.writeByte(slot);
		serializer.writeByte(isMainHand ? PESource.POCKET_OFFHAND : PESource.POCKET_INVENTORY);
		return serializer;
	}

}
