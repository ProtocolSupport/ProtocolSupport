package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPotion;
import protocolsupport.protocol.utils.minecraftdata.PotionData;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class PotionToPEIdSpecificRemapper implements ItemStackComplexRemapper {

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
			if (PEPotion.hasPERemap(potion)) {
				tag.remove("Potion");
				tag.remove("CustomPotionEffects");
				itemstack.setNBT(tag);
				itemstack.setLegacyData(PEPotion.toPEId(potion));
			}
		}
		return itemstack;
	}

}
