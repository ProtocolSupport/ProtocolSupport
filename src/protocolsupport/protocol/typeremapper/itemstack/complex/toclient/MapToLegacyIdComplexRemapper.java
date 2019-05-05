package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTNumber;

public class MapToLegacyIdComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTNumber map;
		if ((itemstack.getNBT() != null) && ((map = itemstack.getNBT().getNumberTag("map")) != null)) {
			itemstack.setLegacyData(map.getAsInt());
		}
		return itemstack;
	}

}