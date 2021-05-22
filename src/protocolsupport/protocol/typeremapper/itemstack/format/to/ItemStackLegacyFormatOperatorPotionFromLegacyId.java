package protocolsupport.protocol.typeremapper.itemstack.format.to;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.ItemMaterialLookup;

public class ItemStackLegacyFormatOperatorPotionFromLegacyId implements ItemStackLegacyFormatOperator {

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		int data = itemstack.getLegacyData();
		String name = LegacyPotionId.fromLegacyId(data);
		if (!StringUtils.isEmpty(name)) {
			CommonNBT.getOrCreateRootTag(itemstack).setTag(CommonNBT.POTION_TYPE, new NBTString(name));
			itemstack.setTypeId(ItemMaterialLookup.getRuntimeId(LegacyPotionId.isThrowable(data) ? Material.SPLASH_POTION : Material.POTION));
		}
		return itemstack;
	}

}
