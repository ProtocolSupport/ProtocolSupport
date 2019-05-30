package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.inventory.PESlotRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class InventorySetItems extends MiddleInventorySetItems {

	public InventorySetItems(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		if (cache.getPEInventoryCache().isInventoryLocked()) {
			return RecyclableEmptyList.get();
		}
		NetworkItemStack[] items = itemstacks.toArray(new NetworkItemStack[itemstacks.size()]);
		return PESlotRemapper.remapClientBoundInventory(cache, version, items);
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, int windowId, NetworkItemStack[] itemstacks) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.INVENTORY_CONTENT);
		VarNumberSerializer.writeVarInt(serializer, windowId);
		VarNumberSerializer.writeVarInt(serializer, itemstacks.length);
		//Also get the nulls for remapped slots in between.
		for (int i = 0; i < itemstacks.length; i++) {
			ItemStackSerializer.writeItemStack(serializer, version, locale, itemstacks[i] == null ? NetworkItemStack.NULL : itemstacks[i]);
		}
		return serializer;
	}

}
