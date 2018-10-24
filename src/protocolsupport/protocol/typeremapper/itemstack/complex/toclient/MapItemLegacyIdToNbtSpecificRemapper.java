package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;

public class MapItemLegacyIdToNbtSpecificRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		//TODO FIX
//		if (tag.isNull()) {
			tag = new NBTCompound();
//		}
//		if(itemstack.getData() != Integer.MAX_VALUE) {
//			tag.setString("map_uuid", String.valueOf(itemstack.getData()));
//			tag.setByte("map_player_display", 1); //TODO: Fix players not showing up.
//			itemstack.setTag(tag);
//			itemstack.setData(0);
//		}
		itemstack.setNBT(tag);
		return itemstack;
	}

}
