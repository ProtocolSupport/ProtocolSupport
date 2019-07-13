package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory.PESource;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.NetworkItemStack;
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
		boolean up19 = version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_9);
		if (entity == null || (entity.getType().equals(NetworkEntityType.ARMOR_STAND_OBJECT) && up19)) {
			return RecyclableEmptyList.get();
		}
		boolean isPlayer = entity.getType().equals(NetworkEntityType.PLAYER);
		NetworkEntityDataCache dataCache = entity.getDataCache();
		NetworkEntityDataCache.Equipment equipment = dataCache.getEquipment();
		if (slot > 1) {
			// Armor update
			switch (slot) {
				case 2: {
					equipment.setBoots(itemstack);
					break;
				}
				case 3: {
					equipment.setLeggings(itemstack);
					break;
				}
				case 4: {
					equipment.setChestplate(itemstack);
					break;
				}
				case 5: {
					equipment.setHelmet(itemstack);
					break;
				}
			}
			return RecyclableSingletonList.create(create(version, locale, entityId, equipment.getHelmet(), equipment.getChestplate(), equipment.getLeggings(), equipment.getBoots()));
		}
		//TODO: hand and offhand on mobs crashes 1.9. why?
		if (up19 && !isPlayer) {
			return RecyclableEmptyList.get();
		}
		if (slot == 1) {
			equipment.setHand(itemstack);
		} else {
			equipment.setOffHand(itemstack);
		}
		return RecyclableSingletonList.create(createUpdateHand(version, locale, entityId, itemstack, cache.getWatchedEntityCache().isSelf(entityId) ? cache.getPEInventoryCache().getSelectedSlot() : 0, slot == 1));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, long entityId, NetworkItemStack helmet, NetworkItemStack chestplate, NetworkItemStack leggings, NetworkItemStack boots) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOB_ARMOR_EQUIPMENT);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		ItemStackSerializer.writeItemStack(serializer, version, locale, helmet);
		ItemStackSerializer.writeItemStack(serializer, version, locale, chestplate);
		ItemStackSerializer.writeItemStack(serializer, version, locale, leggings);
		ItemStackSerializer.writeItemStack(serializer, version, locale, boots);
		return serializer;
	}

	public static ClientBoundPacketData createUpdateHand(ProtocolVersion version, String locale, int entityId, NetworkItemStack itemstack, int slot, boolean isMainHand) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOB_EQUIPMENT);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		ItemStackSerializer.writeItemStack(serializer, version, locale, itemstack);
		serializer.writeByte(slot);
		serializer.writeByte(slot);
		serializer.writeByte(isMainHand ? PESource.POCKET_OFFHAND : PESource.POCKET_INVENTORY);
		return serializer;
	}

}
