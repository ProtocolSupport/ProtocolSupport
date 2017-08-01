package protocolsupport.protocol.typeremapper.itemstack.fromclient;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotion;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class PotionFromLegacyIdRemapper implements ItemStackSpecificRemapper {

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		int data = itemstack.getData();
		String name = LegacyPotion.fromLegacyId(data);
		if (!StringUtils.isEmpty(name)) {
			NBTTagCompoundWrapper tag = itemstack.getTag();
			if (tag.isNull()) {
				tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				itemstack.setTag(tag);
			}
			tag.setString("Potion", name);
			itemstack.setType(LegacyPotion.isThrowable(data) ? Material.SPLASH_POTION : Material.POTION);
			itemstack.setData(0);
		}
		return itemstack;
	}

}
