package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.minecraftdata.MinecraftPotionData;

public class PotionToLegacyIdComplexRemapper implements ItemStackComplexRemapper {

	protected final boolean isThrowablePotion;
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
		NBTList<NBTCompound> customPotionEffects = tag.getTagListOfType("CustomPotionEffects", NBTType.COMPOUND);
		if ((customPotionEffects != null) && (customPotionEffects.size() >= 1)) {
			potion = MinecraftPotionData.getNameById(customPotionEffects.getTag(0).getNumberTag("Id").getAsInt());
		}
		if (potion != null) {
			itemstack.setLegacyData(LegacyPotionId.toLegacyId(potion, isThrowablePotion));
		}
		return itemstack;
	}

}
