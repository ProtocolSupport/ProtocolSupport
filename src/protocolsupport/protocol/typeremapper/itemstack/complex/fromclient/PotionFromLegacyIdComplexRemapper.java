package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotion;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NetworkItemStack;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class PotionFromLegacyIdComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		int data = itemstack.getLegacyData();
		String name = LegacyPotion.fromLegacyId(data);
		if (!StringUtils.isEmpty(name)) {
			NBTTagCompoundWrapper tag = itemstack.getNBT();
			if (tag.isNull()) {
				tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
				itemstack.setNBT(tag);
			}
			tag.setString("Potion", name);
			itemstack.setTypeId(ItemMaterialLookup.getRuntimeId(LegacyPotion.isThrowable(data) ? Material.SPLASH_POTION : Material.POTION));
		}
		return itemstack;
	}

}
