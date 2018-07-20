package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotion;
import protocolsupport.protocol.utils.minecraftdata.PotionData;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class PotionToLegacyIdComplexRemapper implements ItemStackComplexRemapper {

	private final boolean isThrowablePotion;
	public PotionToLegacyIdComplexRemapper(boolean isThrowablePotion) {
		this.isThrowablePotion = isThrowablePotion;
	}

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTTagCompoundWrapper tag = itemstack.getNBT();
		if (tag.isNull()) {
			return itemstack;
		}
		String potion = tag.getString("Potion");
		if (!potion.isEmpty()) {
			NBTTagListWrapper tagList = tag.getList("CustomPotionEffects", NBTTagType.COMPOUND);
			if (tagList.size() >= 1) {
				potion = PotionData.getNameById(tagList.getCompound(0).getIntNumber("Id"));
			}
			itemstack.setLegacyData(LegacyPotion.toLegacyId(potion, isThrowablePotion));
			String basicTypeName = LegacyPotion.getBasicTypeName(potion);
			if (basicTypeName != null) {
//TODO: implement after implementing helper for setting display name via nbt
//				itemstack.setDisplayName(TranslationAPI.translate(locale, basicTypeName));
			}
		}
		return itemstack;
	}

}
