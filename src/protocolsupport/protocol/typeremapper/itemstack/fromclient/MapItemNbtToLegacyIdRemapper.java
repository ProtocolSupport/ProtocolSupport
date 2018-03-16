package protocolsupport.protocol.typeremapper.itemstack.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class MapItemNbtToLegacyIdRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		NBTTagCompoundWrapper tag = itemstack.getTag();
		if (tag != null && !tag.isNull() && tag.hasKeyOfType("map_uuid", NBTTagType.STRING)) {
			try {
				itemstack.setData(Integer.parseInt(tag.getString("map_uuid")));
			} catch (NumberFormatException e) { }
			tag.remove("map_uuid");
			tag.remove("map_player_display");
		}
		return itemstack;
	}

}
