package protocolsupport.protocol.typeremapper.itemstack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapperUtil;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class ItemStackRemapper {

	public static NetworkItemStack remapToClient(ProtocolVersion version, String locale,  NetworkItemStack itemstack) {
		itemstack = ItemStackComplexRemapperUtil.remapToClient(version, locale, itemstack);
		itemstack.setTypeId(LegacyItemType.REGISTRY.getTable(version).getRemap(itemstack.getTypeId()));
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			int legacyCombinedId = PreFlatteningItemIdData.getLegacyCombinedIdByModernId(itemstack.getTypeId());
			itemstack.setTypeId(PreFlatteningItemIdData.getIdFromLegacyCombinedId(legacyCombinedId));
			itemstack.setLegacyData(PreFlatteningItemIdData.getDataFromLegacyCombinedId(legacyCombinedId));
		}
		return itemstack;
	}

	public static NetworkItemStack remapFromClient(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			itemstack.setTypeId(PreFlatteningItemIdData.getModernIdByLegacyIdData(itemstack.getTypeId(), itemstack.getLegacyData()));
		}
		return ItemStackComplexRemapperUtil.remapFromClient(version, locale, itemstack);
	}

}
