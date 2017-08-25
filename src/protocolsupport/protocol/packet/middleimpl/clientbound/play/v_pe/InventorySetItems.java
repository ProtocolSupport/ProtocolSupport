package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.PEInventory.PESource;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class InventorySetItems extends MiddleInventorySetItems {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		String locale = cache.getLocale();
		RecyclableArrayList<ClientBoundPacketData> contentpackets = RecyclableArrayList.create();
		ItemStackWrapper[] items = itemstacks.toArray(new ItemStackWrapper[itemstacks.size()]);
		switch(cache.getOpenedWindow()) {
			case PLAYER: {
				ItemStackWrapper[] peInvGridResult = new ItemStackWrapper[1];
				ItemStackWrapper[] peInvGrid = new ItemStackWrapper[5];
				ItemStackWrapper[] peArmor = new ItemStackWrapper[4];
				ItemStackWrapper[] peInventory = new ItemStackWrapper[36];
				ItemStackWrapper[] peOffhand = new ItemStackWrapper[1];
				System.arraycopy(items, 0, peInvGridResult, 0, 1);
				System.arraycopy(items, 1, peInvGrid, 0, 4);
				System.arraycopy(items, 5, peArmor, 0, 4);
				System.arraycopy(items, 36, peInventory, 0, 9);
				System.arraycopy(items, 9, peInventory, 9, 27);
				System.arraycopy(items, 45, peOffhand, 0, 1);
				contentpackets.add(InventorySetItems.create(version, locale, PESource.POCKET_CRAFTING_RESULT, peInvGridResult));
				contentpackets.add(InventorySetItems.create(version, locale, PESource.POCKET_CRAFTING_GRID, peInvGrid));
				contentpackets.add(InventorySetItems.create(version, locale, PESource.POCKET_ARMOR_EQUIPMENT, peArmor));
				contentpackets.add(InventorySetItems.create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				contentpackets.add(InventorySetItems.create(version, locale, PESource.POCKET_OFFHAND, peOffhand));
				break;
			}
			default: {
				contentpackets.add(InventorySetItems.create(version, locale, windowId, items));
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
		return serializer;
	}
	
}
