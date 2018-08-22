package protocolsupport.protocol.typeremapper.itemstack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapperRegistry;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.utils.IntTuple;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class ItemStackRemapper {

	public static NetworkItemStack remapToClient(ProtocolVersion version, String locale,  NetworkItemStack itemstack) {
		itemstack = ItemStackComplexRemapperRegistry.remapToClient(version, locale, itemstack);
		itemstack.setTypeId(LegacyItemType.REGISTRY.getTable(version).getRemap(itemstack.getTypeId()));
		if (version == ProtocolVersion.MINECRAFT_PE || version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			int legacyCombinedId = PreFlatteningItemIdData.getLegacyCombinedIdByModernId(itemstack.getTypeId());
			itemstack.setTypeId(PreFlatteningItemIdData.getIdFromLegacyCombinedId(legacyCombinedId));
			itemstack.setLegacyData(PreFlatteningItemIdData.getDataFromLegacyCombinedId(legacyCombinedId));
			if (version == ProtocolVersion.MINECRAFT_PE) {
				IntTuple itemAndData = PEDataValues.ITEM_ID.getRemap(itemstack.getTypeId(), itemstack.getLegacyData());
				if (itemAndData != null) {
					itemstack.setTypeId(itemAndData.getI1());
					if (itemAndData.getI2() != -1) {
						itemstack.setLegacyData(itemAndData.getI2());
					}
				}
			}
		}
		return itemstack;
	}

	public static NetworkItemStack remapFromClient(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (version == ProtocolVersion.MINECRAFT_PE || version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			itemstack.setTypeId(PreFlatteningItemIdData.getModernIdByLegacyIdData(itemstack.getTypeId(), itemstack.getLegacyData()));
			if (version == ProtocolVersion.MINECRAFT_PE) {
				int prevData = itemstack.getLegacyData();
				IntTuple itemAndData = PEDataValues.PE_ITEM_ID.getRemap(itemstack.getTypeId(), itemstack.getLegacyData());
				if (itemAndData != null) {
					itemstack.setTypeId(itemAndData.getI1());
					if (itemAndData.getI2() != -1) {
						itemstack.setLegacyData(itemAndData.getI2());
					} else {
						itemstack.setLegacyData(prevData); // changing the item ID resets the data to 0
					}
				}
			}
		}
		return ItemStackComplexRemapperRegistry.remapFromClient(version, locale, itemstack);
	}

}