package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class MapToLegacyIdComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (!itemstack.getNBT().isNull() && itemstack.getNBT().hasKeyOfType("map", NBTTagType.INT)) {
			itemstack.setLegacyData(itemstack.getNBT().getIntNumber("map"));
		}
		return itemstack;
	}

}