package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class MapItemLegacyIdToNbtSpecificRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTTagCompoundWrapper tag = itemstack.getNBT();
		//TODO FIX
//		if (tag.isNull()) {
			tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
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
