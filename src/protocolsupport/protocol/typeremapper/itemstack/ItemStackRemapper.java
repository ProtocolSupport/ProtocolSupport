package protocolsupport.protocol.typeremapper.itemstack;

import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapperRegistry;
import protocolsupport.protocol.typeremapper.pe.PEItems;
import protocolsupport.protocol.types.NetworkItemStack;

public class ItemStackRemapper {

	public static NetworkItemStack remapToClient(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		itemstack = ItemStackComplexRemapperRegistry.remapToClient(version, locale, itemstack);
		itemstack.setTypeId(LegacyItemType.REGISTRY.getTable(version).getRemap(itemstack.getTypeId()));
		if (version.getProtocolType() == ProtocolType.PE) {
			int peCombinedId = PEItems.getPECombinedIdByModernId(itemstack.getTypeId());
			int peData = PEItems.getDataFromPECombinedId(peCombinedId);
			itemstack.setTypeId(PEItems.getIdFromPECombinedId(peCombinedId));
			if (!isComplexlyRemapped(itemstack)) { //preserve legacy durability data
				itemstack.setLegacyData(peData);
			}
		} else if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			int legacyCombinedId = PreFlatteningItemIdData.getLegacyCombinedIdByModernId(itemstack.getTypeId());
			itemstack.setTypeId(PreFlatteningItemIdData.getIdFromLegacyCombinedId(legacyCombinedId));
			if (!isComplexlyRemapped(itemstack)) {
				itemstack.setLegacyData(PreFlatteningItemIdData.getDataFromLegacyCombinedId(legacyCombinedId));
			}
		} else {
			itemstack.setTypeId(FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version).getRemap(itemstack.getTypeId()));
		}
		return itemstack;
	}

	public static NetworkItemStack remapFromClient(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (version.getProtocolType() == ProtocolType.PE) {
			int modernId = PEItems.getModernIdByPEIdData(itemstack.getTypeId(), itemstack.getLegacyData());
			itemstack.setTypeId(modernId);
			itemstack.setLegacyData(NetworkItemStack.DEFAULT_LEGACY_DATA);
		} else if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			int legacyCombinedId = PreFlatteningItemIdData.formLegacyCombinedId(itemstack.getTypeId(), itemstack.getLegacyData());
			itemstack.setTypeId(PreFlatteningItemIdData.getModernIdByLegacyCombinedId(legacyCombinedId));
		} else {
			itemstack.setTypeId(FlatteningItemId.REGISTRY_FROM_CLIENT.getTable(version).getRemap(itemstack.getTypeId()));
		}
		return ItemStackComplexRemapperRegistry.remapFromClient(version, locale, itemstack);
	}

	public static boolean isComplexlyRemapped(NetworkItemStack itemstack) {
		return itemstack.getLegacyData() != NetworkItemStack.DEFAULT_LEGACY_DATA;
	}

}
