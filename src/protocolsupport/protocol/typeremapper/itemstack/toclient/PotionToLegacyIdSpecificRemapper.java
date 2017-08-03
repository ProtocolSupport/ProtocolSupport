package protocolsupport.protocol.typeremapper.itemstack.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.TranslationAPI;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotion;
import protocolsupport.protocol.utils.minecraftdata.PotionData;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class PotionToLegacyIdSpecificRemapper implements ItemStackSpecificRemapper {

	private final boolean isThrowablePotion;
	public PotionToLegacyIdSpecificRemapper(boolean isThrowablePotion) {
		this.isThrowablePotion = isThrowablePotion;
	}

	@Override
	public ItemStackWrapper remap(ProtocolVersion version, String locale, ItemStackWrapper itemstack) {
		NBTTagCompoundWrapper tag = itemstack.getTag();
		if (tag.isNull()) {
			return itemstack;
		}
		String potion = tag.getString("Potion");
		if (!potion.isEmpty()) {
			NBTTagListWrapper tagList = tag.getList("CustomPotionEffects", NBTTagType.COMPOUND);
			if (tagList.size() >= 1) {
				potion = PotionData.getNameById(tagList.getCompound(0).getIntNumber("Id"));
			}
			itemstack.setData(LegacyPotion.toLegacyId(potion, isThrowablePotion));
			String basicTypeName = LegacyPotion.getBasicTypeName(potion);
			if (basicTypeName != null) {
				itemstack.setDisplayName(TranslationAPI.translate(locale, basicTypeName));
			}
		}
		return itemstack;
	}

}
