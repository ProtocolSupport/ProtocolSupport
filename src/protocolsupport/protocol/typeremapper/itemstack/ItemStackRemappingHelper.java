package protocolsupport.protocol.typeremapper.itemstack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormat;
import protocolsupport.protocol.typeremapper.itemstack.legacy.ItemStackLegacyData;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.ItemStackWriteEventHelper;
import protocolsupport.protocol.utils.i18n.I18NData;

public class ItemStackRemappingHelper {

	public static NetworkItemStack toLegacyItemData(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (itemstack.isNull()) {
			return itemstack;
		}

		ItemStackWriteEventHelper.callEvent(version, locale, itemstack);
		return ItemStackLegacyData.REGISTRY.getTable(version).apply(itemstack);
	}


	public static NetworkItemStack toLegacyItemFormat(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		if (itemstack.isNull()) {
			return itemstack;
		}

		itemstack = ItemStackLegacyFormat.REGISTRY_TO.getTable(version).apply(locale, itemstack);
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			int legacyCombinedId = PreFlatteningItemIdData.getLegacyCombinedIdByModernId(itemstack.getTypeId());
			itemstack.setTypeId(PreFlatteningItemIdData.getIdFromLegacyCombinedId(legacyCombinedId));
			if (!isLegacyDataSet(itemstack)) {
				itemstack.setLegacyData(PreFlatteningItemIdData.getDataFromLegacyCombinedId(legacyCombinedId));
			}
		} else {
			itemstack.setTypeId(FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version).get(itemstack.getTypeId()));
		}
		return itemstack;
	}

	public static NetworkItemStack fromLegacyItemFormat(ProtocolVersion version, NetworkItemStack itemstack) {
		if (itemstack.isNull()) {
			return itemstack;
		}

		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			itemstack.setTypeId(PreFlatteningItemIdData.getModernIdByLegacyIdData(itemstack.getTypeId(), itemstack.getLegacyData()));
		} else {
			itemstack.setTypeId(FlatteningItemId.REGISTRY_FROM_CLIENT.getTable(version).get(itemstack.getTypeId()));
		}
		return ItemStackLegacyFormat.REGISTRY_FROM.getTable(version).apply(I18NData.DEFAULT_LOCALE, itemstack);
	}


	public static NetworkItemStack toLegacyItemDataFormat(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		return toLegacyItemFormat(version, locale, toLegacyItemData(version, locale, itemstack));
	}

	public static int toLegacyItemDataFormat(ProtocolVersion version, int itemId) {
		NetworkItemStack itemstack = new NetworkItemStack(itemId);
		itemstack = ItemStackLegacyData.REGISTRY.getTable(version).apply(itemstack);
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13)) {
			return FlatteningItemId.REGISTRY_TO_CLIENT.getTable(version).get(itemstack.getTypeId());
		} else {
			return PreFlatteningItemIdData.getIdFromLegacyCombinedId(PreFlatteningItemIdData.getLegacyCombinedIdByModernId(itemstack.getTypeId()));
		}
	}


	public static boolean isLegacyDataSet(NetworkItemStack itemstack) {
		return itemstack.getLegacyData() != NetworkItemStack.DEFAULT_LEGACY_DATA;
	}

}
