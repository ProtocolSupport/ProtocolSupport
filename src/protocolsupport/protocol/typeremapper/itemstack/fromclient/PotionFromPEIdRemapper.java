package protocolsupport.protocol.typeremapper.itemstack.fromclient;

import org.apache.commons.lang3.StringUtils;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPotion;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class PotionFromPEIdRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		int data = itemstack.getData();
		String name = PEPotion.fromPEId(data);
		if (!StringUtils.isEmpty(name)) {
			NBTTagCompoundWrapper tag = itemstack.getTag();
			if (tag.isNull()) {
				tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				itemstack.setTag(tag);
			}
			tag.setString("Potion", name);
			itemstack.setData(0);
		}
		return itemstack;
	}

}
