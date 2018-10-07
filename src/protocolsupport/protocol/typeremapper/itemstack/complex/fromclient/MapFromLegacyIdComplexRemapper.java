package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class MapFromLegacyIdComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		System.out.println("MAP: " + itemstack.getLegacyData());
		NBTTagCompoundWrapper tag = itemstack.getNBT();
		if (tag.isNull()) {
			tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			itemstack.setNBT(tag);
		}
		tag.setInt("map", itemstack.getLegacyData());
		return itemstack;
	}

}