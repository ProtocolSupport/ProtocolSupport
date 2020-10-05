package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;

public class BedToLegacyComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		itemstack.setLegacyData(0);
		return itemstack;
	}

}
