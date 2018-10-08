package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.utils.types.NetworkItemStack;

public class ItemDurabilityToLegacyDataComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (itemstack.getNBT() != null && itemstack.getNBT().getTag("Damage") != null) {
			itemstack.setLegacyData(itemstack.getNBT().getNumberTag("Damage").getAsInt());
		}
		return itemstack;
	}


}
