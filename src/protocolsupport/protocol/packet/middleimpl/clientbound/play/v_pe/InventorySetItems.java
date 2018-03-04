package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.typeremapper.pe.PEInventory.PESource;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class InventorySetItems extends MiddleInventorySetItems {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		if(invCache.isInventoryLocked()) {
			return RecyclableEmptyList.get();
		}
		String locale = cache.getAttributesCache().getLocale();
		RecyclableArrayList<ClientBoundPacketData> contentpackets = RecyclableArrayList.create();
		ItemStackWrapper[] items = itemstacks.toArray(new ItemStackWrapper[itemstacks.size()]);
		switch(cache.getWindowCache().getOpenedWindow()) {
			case PLAYER: {
				ItemStackWrapper[] peInvGridResult = new ItemStackWrapper[1];
				ItemStackWrapper[] peInvGrid = new ItemStackWrapper[5];
				ItemStackWrapper[] peArmor = new ItemStackWrapper[4];
				ItemStackWrapper[] peInventory = new ItemStackWrapper[36];
				ItemStackWrapper[] peOffhand = new ItemStackWrapper[1];
				System.arraycopy(items,  0, peInvGridResult, 0 , 1);
				System.arraycopy(items,  1, peInvGrid, 		 0,  4);
				System.arraycopy(items,  5, peArmor, 		 0,  4);
				System.arraycopy(items, 36, peInventory, 	 0,  9);
				System.arraycopy(items,  9, peInventory, 	 9, 27);
				System.arraycopy(items, 45, peOffhand, 		 0,  1);
				contentpackets.add(create(version, locale, PESource.POCKET_CRAFTING_RESULT, peInvGridResult));
				contentpackets.add(create(version, locale, PESource.POCKET_CRAFTING_GRID_ADD, peInvGrid));
				contentpackets.add(create(version, locale, PESource.POCKET_ARMOR_EQUIPMENT, peArmor));
				contentpackets.add(create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				contentpackets.add(create(version, locale, PESource.POCKET_OFFHAND, peOffhand));
				break;
			}
			case BREWING: {
				ItemStackWrapper[] brewingSlots = new ItemStackWrapper[5];
				ItemStackWrapper[] peInventory = new ItemStackWrapper[36];
				System.arraycopy(items,  0, brewingSlots, 1,  3);
				System.arraycopy(items,  3, brewingSlots, 0,  1);
				System.arraycopy(items,  4, brewingSlots, 4,  1);
				System.arraycopy(items, 32, peInventory,  0,  9);
				System.arraycopy(items,  5, peInventory,  9, 27);
				contentpackets.add(create(version, locale, windowId, brewingSlots));
				contentpackets.add(create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			case ANVIL: {
				ItemStackWrapper[] peAnvIn = new ItemStackWrapper[1];
				ItemStackWrapper[] peAnvMa = new ItemStackWrapper[1];
				ItemStackWrapper[] peAnvOu = new ItemStackWrapper[1];
				ItemStackWrapper[] peInventory = new ItemStackWrapper[36];
				System.arraycopy(items,  0, peAnvIn, 	 0,  1);
				System.arraycopy(items,  1, peAnvMa, 	 0,  1);
				System.arraycopy(items,  2, peAnvOu,     0,  1);
				System.arraycopy(items, 30, peInventory, 0,  9);
				System.arraycopy(items,  3, peInventory, 9, 27);
				contentpackets.add(create(version, locale, PESource.POCKET_ANVIL_INPUT, peAnvIn));
				contentpackets.add(create(version, locale, PESource.POCKET_ANVIL_MATERIAL, peAnvMa));
				contentpackets.add(create(version, locale, PESource.POCKET_ANVIL_OUTPUT, peAnvOu));
				contentpackets.add(create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			case ENCHANT: { //Faked with hopper thingy, server sends the two slots though.
				ItemStackWrapper[] peInventory = new ItemStackWrapper[36];
				invCache.getEnchantHopper().setInputOutputStack(items[0]);
				invCache.getEnchantHopper().setLapisStack(items[1]);
				System.arraycopy(items, 29, peInventory, 0,  9);
				System.arraycopy(items,  2, peInventory, 9, 27);
				contentpackets.add(invCache.getEnchantHopper().updateInventory(cache, version));
				contentpackets.add(create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			case BEACON: {
				ItemStackWrapper[] peBeaMa = new ItemStackWrapper[1];
				ItemStackWrapper[] peInventory = new ItemStackWrapper[36];
				System.arraycopy(items,  0, peBeaMa, 	 0,  1);
				System.arraycopy(items, 28, peInventory, 0,  9);
				System.arraycopy(items,  1, peInventory, 9, 27);
				contentpackets.add(create(version, locale, PESource.POCKET_BEACON, peBeaMa));
				contentpackets.add(create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			case CRAFTING_TABLE: {
				ItemStackWrapper[] craftingGridResult = new ItemStackWrapper[1];
				ItemStackWrapper[] craftingSlots = new ItemStackWrapper[9];
				ItemStackWrapper[] peInventory = new ItemStackWrapper[36];
				System.arraycopy(items,  0, craftingGridResult, 0 , 1);
				System.arraycopy(items,  1, craftingSlots,	 	0,  9);
				System.arraycopy(items, 37, peInventory, 		0,  9);
				System.arraycopy(items, 10, peInventory, 		9, 27);
				contentpackets.add(create(version, locale, PESource.POCKET_CRAFTING_RESULT, craftingGridResult));
				contentpackets.add(create(version, locale, PESource.POCKET_CRAFTING_GRID_ADD, craftingSlots));
				contentpackets.add(create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
			default: {
				int wSlots = cache.getWindowCache().getOpenedWindowSlots();
				int peWSlots = wSlots;
				if(wSlots > 16) { 
					//PE only has doublechest or single chest interface for high slots.
					wSlots = wSlots / 9 * 9;
					peWSlots = wSlots > 27 ? 54 : 27;
				}
				ItemStackWrapper[] windowSlots = new ItemStackWrapper[peWSlots];
				ItemStackWrapper[] peInventory = new ItemStackWrapper[36];
				System.arraycopy(items,		 	  0, 	windowSlots, 0, wSlots);
				System.arraycopy(items, wSlots + 27,	peInventory, 0,	 	 9);
				System.arraycopy(items, 	 wSlots, 	peInventory, 9, 	27);
				contentpackets.add(create(version, locale, windowId, windowSlots));
				contentpackets.add(create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				break;
			}
		}
		return contentpackets;
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, String locale, int windowId, ItemStackWrapper[] itemstacks) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.INVENTORY_CONTENT, version);
		VarNumberSerializer.writeVarInt(serializer, windowId);
		VarNumberSerializer.writeVarInt(serializer, itemstacks.length);
		//Also get the nulls for remapped slots in between ;)
		for (int i = 0; i < itemstacks.length; i++) {
			ItemStackSerializer.writeItemStack(serializer, version, locale, itemstacks[i], true);
		}
		//System.out.println("Sending inventory contents of " + windowId);
		return serializer;
	}
	
}
