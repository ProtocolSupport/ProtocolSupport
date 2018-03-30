package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.inventory.PESlotRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class InventorySetItems extends MiddleInventorySetItems {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		if(cache.getPEInventoryCache().isInventoryLocked()) {
			return RecyclableEmptyList.get();
		}
		ItemStackWrapper[] items = itemstacks.toArray(new ItemStackWrapper[itemstacks.size()]);
		return PESlotRemapper.remapClientBoundInventory(cache, version, items);
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, int windowId, ItemStackWrapper[] itemstacks) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.INVENTORY_CONTENT);
		VarNumberSerializer.writeVarInt(serializer, windowId);
		VarNumberSerializer.writeVarInt(serializer, itemstacks.length);
		//Also get the nulls for remapped slots in between.
		for (int i = 0; i < itemstacks.length; i++) {
			ItemStackSerializer.writeItemStack(serializer, version, locale, itemstacks[i], true);
		}
		return serializer;
	}

}
