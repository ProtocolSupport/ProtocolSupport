package protocolsupport.protocol.typeremapper.itemstack.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class MapItemLegacyIdToNbtSpecificRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		NBTTagCompoundWrapper tag = itemstack.getTag();
		if (tag.isNull()) {
			tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
		}
		tag.setString("map_uuid", String.valueOf(itemstack.getData()));
		itemstack.setTag(tag);
		itemstack.setData(0);
		return itemstack;
	}

}
