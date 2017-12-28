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
		if (cache.isInventoryLocked()) {
			return RecyclableEmptyList.get();
		}
		ProtocolVersion version = connection.getVersion();
		String locale = cache.getLocale();
		if (slot == -1) {
			//TODO: Figure out how to not fuck this up.
			//Cursor slot can be set by plugin (only if a window is actually open), this will cause issues however with the deficit/surplus stack so we add them manually here.
			cache.getInfTransactions().customCursorSurplus(cache, itemstack);
			return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_CLICKED_SLOT, 0, itemstack));
		}
		switch(cache.getOpenedWindow()) {
			case PLAYER: {
				if (slot == 0) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_CRAFTING_RESULT, 0, itemstack));
				} else if (slot <= 4) {
					//TODO: Test?
					if (itemstack.isNull()) {
						return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_CRAFTING_GRID_REMOVE, slot - 1, itemstack));
					} else {
						return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_CRAFTING_GRID_ADD, slot - 1, itemstack));
					}
				} else if (slot <= 8) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_ARMOR_EQUIPMENT, slot - 5, itemstack));
				} else if (slot <= 35) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot, itemstack));
				} else if (slot <= 44) {
					if(slot - 36 == cache.getSelectedSlot()) { cache.setItemInHand(itemstack); }
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot - 36, itemstack));
				} else if (slot == 45) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_OFFHAND, 0, itemstack));
				}
				break;
			}
			case BREWING: {
				if (slot == 3) {
					slot = 0;
				} else if (slot <= 2) {
					slot += 1;
				} else if (slot > 4) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, (slot >= 32 ? slot - 32 : slot + 4), itemstack));
				}
				return RecyclableSingletonList.create(create(version, locale, windowId, slot, itemstack));
			}
			case ANVIL: {
				switch(slot) {
					case 0: {
						return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_ANVIL_INPUT, 0, itemstack));
					}
					case 1: {
						return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_ANVIL_MATERIAL, 0, itemstack));
					}
					case 2: {
						return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_ANVIL_OUTPUT, 0, itemstack));
					}
					default: {
						return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot >= 30 ? slot - 30 : slot + 6, itemstack));
					}
				}
			}
			case ENCHANT: {
				if (slot == 0) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_ENCHANT_OUTPUT, 0, itemstack));
				} else if (slot == 1) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_ENCHANT_MATERIAL, 0, itemstack));
				} else {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot >= 29 ? slot - 29 : slot + 7, itemstack));
				}
			}
			case BEACON: {
				if (slot == 0) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_BEACON, 0, itemstack));
				} else {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot >= 28 ? slot - 28 : slot + 8, itemstack));
				}
			}
			case CRAFTING_TABLE: {
				if (slot == 0) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_CRAFTING_RESULT, 0, itemstack));
				} else if (slot < 10) {
					if (itemstack.isNull()) {
						return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_CRAFTING_GRID_REMOVE, slot - 1, itemstack));
					} else {
						return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_CRAFTING_GRID_ADD, slot - 1, itemstack));
					}
				} else if (slot >= 37) {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot - 37, itemstack));
				} else {
					return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot - 1, itemstack));
				}
			}
			default: {
				int wSlots = cache.getOpenedWindowSlots();
				//Makes malformated inventory slot amounts to work. (Essentials's /invsee for example)
				if(wSlots > 16) { wSlots = wSlots / 9 * 9; }
				if (slot > wSlots) {
					slot -= wSlots;
					if (slot >= 27) {
						return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot - 27, itemstack));
					} else {
						return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_INVENTORY, slot + 9, itemstack));
					}
				}
				return RecyclableSingletonList.create(create(version, locale, windowId, slot, itemstack));
			}
		}
		return RecyclableEmptyList.get();
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, String locale, int windowId, int slot, ItemStackWrapper itemstack) {
		System.err.println("SLOTTTY: " + slot);
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.INVENTORY_SLOT, version);
		VarNumberSerializer.writeVarInt(serializer, windowId);
		VarNumberSerializer.writeVarInt(serializer, slot);
		ItemStackSerializer.writeItemStack(serializer, version, locale, itemstack, true);
		return serializer;
	}

}
