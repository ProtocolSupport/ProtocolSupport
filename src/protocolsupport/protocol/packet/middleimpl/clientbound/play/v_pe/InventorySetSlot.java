package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PEInventoryCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory.PESource;
import protocolsupport.protocol.typeremapper.pe.inventory.PESlotRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetSlot extends MiddleInventorySetSlot {

	public InventorySetSlot(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		PEInventoryCache invCache = cache.getPEInventoryCache();
		if (invCache.isInventoryLocked()) {
			return RecyclableEmptyList.get();
		}
		String locale = cache.getAttributesCache().getLocale();
		if (slot == -1) {
			//Cursor slot can be set by plugin (only if a window is actually open), this will cause issues however with the deficit/surplus stack so we add them manually here.
			invCache.getTransactionRemapper().customCursor(itemstack);
			return RecyclableSingletonList.create(create(version, locale, PESource.POCKET_CLICKED_SLOT, 0, itemstack));
		}
		ClientBoundPacketData remapped = PESlotRemapper.remapClientBoundSlot(cache, version, itemstack, slot);
		if (remapped != null) {
			return RecyclableSingletonList.create(remapped);
		}

		return RecyclableEmptyList.get();
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, int windowId, int slot, NetworkItemStack itemstack) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.INVENTORY_SLOT);
		VarNumberSerializer.writeVarInt(serializer, windowId);
		VarNumberSerializer.writeVarInt(serializer, slot);
		ItemStackSerializer.writeItemStack(serializer, version, locale, itemstack);
		return serializer;
	}

}
