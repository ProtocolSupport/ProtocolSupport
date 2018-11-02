package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPotion;
import protocolsupport.protocol.utils.minecraftdata.PotionData;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTList;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class PotionToPEIdSpecificRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag == null) {
			return itemstack;
		}
		NBTString potionTag = tag.getTagOfType("Potion", NBTType.STRING);
		if (potionTag != null && !potionTag.getValue().isEmpty()) {
			String potion = potionTag.getValue();
			NBTList<NBTCompound> tagList = tag.getTagListOfType("CustomPotionEffects", NBTType.COMPOUND);
			if (tagList != null && tagList.size() >= 1) {
				potion = PotionData.getNameById(tagList.getTag(0).getNumberTag("Id").getAsInt());
			}
			if (PEPotion.hasPERemap(potion)) {
				tag.removeTag("Potion");
				tag.removeTag("CustomPotionEffects");
				itemstack.setNBT(tag);
				itemstack.setLegacyData(PEPotion.toPEId(potion));
			}
		}
		return itemstack;
	}

}
