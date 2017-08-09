package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityEquipment extends MiddleEntityEquipment {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (this.slot > 1) {
			// Armor update
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOB_ARMOR_EQUIPMENT, connection.getVersion());
			VarNumberSerializer.writeVarLong(serializer, this.entityId);
			NetworkEntity.DataCache dataCache = this.cache.getWatchedEntity(this.entityId).getDataCache();
			switch (this.slot) {
				case (2): {
					dataCache.setBoots(this.itemstack);
					break;
				}
				case (3): {
					dataCache.setLeggings(this.itemstack);
					break;
				}
				case (4): {
					dataCache.setChestplate(this.itemstack);
					break;
				}
				case (5): {
					dataCache.setHelmet(this.itemstack);
					break;
				}
			}
			ItemStackSerializer.writeItemStack(serializer, ProtocolVersion.MINECRAFT_PE, this.cache.getLocale(), dataCache.getHelmet(), true); // helmet
			ItemStackSerializer.writeItemStack(serializer, ProtocolVersion.MINECRAFT_PE, this.cache.getLocale(), dataCache.getChestplate(), true); // chestplate
			ItemStackSerializer.writeItemStack(serializer, ProtocolVersion.MINECRAFT_PE, this.cache.getLocale(), dataCache.getLeggings(), true); // leggings
			ItemStackSerializer.writeItemStack(serializer, ProtocolVersion.MINECRAFT_PE, this.cache.getLocale(), dataCache.getBoots(), true); // boots

			return RecyclableSingletonList.create(serializer);
		} else if (this.slot == 0) { // Main hand update
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.MOB_EQUIPMENT, connection.getVersion());
			VarNumberSerializer.writeVarLong(serializer, this.entityId);
			ItemStackSerializer.writeItemStack(serializer, ProtocolVersion.MINECRAFT_PE, this.cache.getLocale(), this.itemstack, true);
			serializer.writeByte(this.slot); // Inventory slot (I wonder why the client needs to care about this...)
			serializer.writeByte(this.slot); // Hotbar slot (again, why?)
			serializer.writeByte(0); // Container ID

			return RecyclableSingletonList.create(serializer);
		} else {
			// Offhand update
			return RecyclableEmptyList.get();
		}
	}
	
}
