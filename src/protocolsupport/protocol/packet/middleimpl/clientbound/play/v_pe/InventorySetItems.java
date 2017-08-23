package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class InventorySetItems extends MiddleInventorySetItems {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableEmptyList.get();
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, String locale, WindowType windowType, int windowId, ItemStackWrapper[] itemstacks) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.INVENTORY_CONTENT, version);
		VarNumberSerializer.writeVarInt(serializer, windowId);
		VarNumberSerializer.writeVarInt(serializer, itemstacks.length);
		for (int i = 0; i < itemstacks.length; i++) { //Also get the nulls for remapped slots in between ;)
			ItemStackSerializer.writeItemStack(serializer, version, locale, itemstacks[i], true);
		}
		System.out.println("YEE!");
		return serializer;
	}

}
