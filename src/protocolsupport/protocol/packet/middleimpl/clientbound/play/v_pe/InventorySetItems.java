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
		switch(cache.getOpenedWindow()) {
			case PLAYER: {
				ItemStackWrapper[] peInvGridResult = new ItemStackWrapper[1];
				ItemStackWrapper[] peInvGrid = new ItemStackWrapper[5];
				ItemStackWrapper[] peArmor = new ItemStackWrapper[4];
				ItemStackWrapper[] peInventory = new ItemStackWrapper[36];
				ItemStackWrapper[] peOffhand = new ItemStackWrapper[1];
				for (int i = 0; i < itemstacks.size(); i++) {
					if (i == 0) {
						peInvGridResult[0] = itemstacks.get(i);
					} else if (i <= 4) {
						peInvGrid[i - 1] = itemstacks.get(i);
					} else if (i <= 8) {
						peArmor[i - 5] = itemstacks.get(i);
					} else if (i <= 35) {
						peInventory[i] = itemstacks.get(i);
					} else if (i <= 44) {
						peInventory[i - 36] = itemstacks.get(i);
					} else if (i == 45) {
						peOffhand[0] = itemstacks.get(i);
					}
				}
				contentpackets.add(InventorySetItems.create(version, locale, PESource.POCKET_CRAFTING_RESULT, peInvGridResult));
				contentpackets.add(InventorySetItems.create(version, locale, PESource.POCKET_CRAFTING_GRID, peInvGrid));
				contentpackets.add(InventorySetItems.create(version, locale, PESource.POCKET_ARMOR_EQUIPMENT, peArmor));
				contentpackets.add(InventorySetItems.create(version, locale, PESource.POCKET_INVENTORY, peInventory));
				contentpackets.add(InventorySetItems.create(version, locale, PESource.POCKET_OFFHAND, peOffhand));
				break;
			}
			default: {
				contentpackets.add(InventorySetItems.create(version, locale, windowId, (ItemStackWrapper[]) itemstacks.toArray()));
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
