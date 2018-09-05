package protocolsupport.protocol.typeremapper.itemstack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapperRegistry;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class ItemStackRemapper {

	public static NetworkItemStack remapToClient(ProtocolVersion version, String locale,  NetworkItemStack itemstack) {
		itemstack = ItemStackComplexRemapperRegistry.remapToClient(version, locale, itemstack);
		itemstack.setTypeId(LegacyItemType.REGISTRY.getTable(version).getRemap(itemstack.getTypeId()));
		if ((version == ProtocolVersion.MINECRAFT_PE) || version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			int legacyCombinedId = PreFlatteningItemIdData.getLegacyCombinedIdByModernId(itemstack.getTypeId());
			itemstack.setTypeId(PreFlatteningItemIdData.getIdFromLegacyCombinedId(legacyCombinedId));
			itemstack.setLegacyData(PreFlatteningItemIdData.getDataFromLegacyCombinedId(legacyCombinedId));
			if (version == ProtocolVersion.MINECRAFT_PE) {
				int peCombinedId = PEDataValues.pcToPeItem(legacyCombinedId);
				itemstack.setTypeId(PreFlatteningItemIdData.getIdFromLegacyCombinedId(peCombinedId));
				itemstack.setLegacyData(PreFlatteningItemIdData.getDataFromLegacyCombinedId(peCombinedId));
			}
		} else {
			itemstack.setTypeId(FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version).getRemap(itemstack.getTypeId()));
		}
		return itemstack;
	}

	public static NetworkItemStack remapFromClient(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if ((version == ProtocolVersion.MINECRAFT_PE) || version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			int legacyCombinedId = PreFlatteningItemIdData.formLegacyCombinedId(itemstack.getTypeId(), itemstack.getLegacyData());
			itemstack.setTypeId(PreFlatteningItemIdData.getModernIdByLegacyCombinedId(legacyCombinedId));
			if (version == ProtocolVersion.MINECRAFT_PE) {
				int peCombinedId = PEDataValues.pcToPeItem(legacyCombinedId);
				itemstack.setTypeId(PreFlatteningItemIdData.getIdFromLegacyCombinedId(peCombinedId));
				itemstack.setLegacyData(PreFlatteningItemIdData.getDataFromLegacyCombinedId(peCombinedId));
			}
		} else {
			itemstack.setTypeId(FlatteningItemId.REGISTRY_FROM_CLIENT.getTable(version).getRemap(itemstack.getTypeId()));
		}
		return ItemStackComplexRemapperRegistry.remapFromClient(version, locale, itemstack);
	}

}