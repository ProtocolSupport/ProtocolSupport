package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.utils.CommonNBT;

public class MapToLegacyIdComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTNumber mapId;
		if ((itemstack.getNBT() != null) && ((mapId = itemstack.getNBT().getNumberTagOrNull(CommonNBT.MAP_ID)) != null)) {
			itemstack.setLegacyData(mapId.getAsInt());
		}
		return itemstack;
	}

}