package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;

public class SpawnEggToIntIdComplexRemapper implements ItemStackComplexRemapper {

	protected final int intId;
	public SpawnEggToIntIdComplexRemapper(int intId) {
		this.intId = intId;
	}

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		itemstack.setLegacyData(intId);
		return itemstack;
	}

}
