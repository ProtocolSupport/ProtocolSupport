package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.TranslationAPI;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.minecraftdata.PotionData;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTList;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class PotionToLegacyIdComplexRemapper implements ItemStackComplexRemapper {

	private final boolean isThrowablePotion;
	public PotionToLegacyIdComplexRemapper(boolean isThrowablePotion) {
		this.isThrowablePotion = isThrowablePotion;
	}

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag == null) {
			return itemstack;
		}
		String potion = NBTString.getValueOrNull(tag.getTagOfType("Potion", NBTType.STRING));
		if (potion != null) {
			NBTList<NBTCompound> customPotionEffects = tag.getTagListOfType("CustomPotionEffects", NBTType.COMPOUND);
			if (customPotionEffects.size() >= 1) {
				potion = PotionData.getNameById(customPotionEffects.getTag(0).getNumberTag("Id").getAsInt());
			}
			itemstack.setLegacyData(LegacyPotionId.toLegacyId(potion, isThrowablePotion));
			String basicTypeName = LegacyPotionId.getBasicTypeName(potion);
			if (basicTypeName != null) {
				NBTCompound display = CommonNBT.getOrCreateDisplayTag(tag);
				display.setTag(CommonNBT.DISPLAY_NAME, new NBTString(ChatAPI.toJSON(new TextComponent(TranslationAPI.translate(locale, basicTypeName)))));
			}
		}
		return itemstack;
	}

}
