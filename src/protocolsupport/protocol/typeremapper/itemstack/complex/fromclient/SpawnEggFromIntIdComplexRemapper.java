package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.ItemSpawnEggData;

public class SpawnEggFromIntIdComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		int eggTypeId = ItemSpawnEggData.getMaterialIdBySpawnedType(LegacyEntityId.getTypeByIntId(itemstack.getLegacyData()));
		if (eggTypeId != -1) {
			itemstack.setTypeId(eggTypeId);
		}
		return itemstack;
	}

}
