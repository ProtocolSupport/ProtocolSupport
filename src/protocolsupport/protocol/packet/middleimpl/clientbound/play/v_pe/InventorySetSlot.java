package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEInventory.PESource;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class InventorySetSlot extends MiddleInventorySetSlot {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		String locale = cache.getLocale();
		switch(cache.getOpenedWindow()) {
			case PLAYER: {
				if (slot == 0) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_CRAFTING_RESULT, 0, itemstack));
				} else if (slot <= 4) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_CRAFTING_GRID, slot - 1, itemstack));
				} else if (slot <= 8) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_ARMOR_EQUIPMENT, slot - 5, itemstack));
				} else if (slot <= 35) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot, itemstack));
				} else if (slot <= 44) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot - 36, itemstack));
				} else if (slot == 45) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_OFFHAND, 0, itemstack));
				}
				break;
			}
			default: {
				return RecyclableSingletonList.create(create(version, locale, windowId, slot, itemstack));
			}
		}
		return RecyclableEmptyList.get();
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, String locale, int windowId, int slot, ItemStackWrapper itemstack) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.INVENTORY_SLOT, version);
		VarNumberSerializer.writeVarInt(serializer, windowId);
		VarNumberSerializer.writeVarInt(serializer, slot);
		ItemStackSerializer.writeItemStack(serializer, version, locale, itemstack, true);
		System.out.println(slot);
		return serializer;
	}

}
