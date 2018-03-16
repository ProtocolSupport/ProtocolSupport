package protocolsupport.protocol.typeremapper.itemstack.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackSpecificRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPotion;
import protocolsupport.protocol.utils.minecraftdata.PotionData;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class PotionToPEIdSpecificRemapper implements ItemStackSpecificRemapper {

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
			if (PEPotion.hasPERemap(potion)) {
				tag.remove("Potion");
				tag.remove("CustomPotionEffects");
				itemstack.setTag(tag);
				itemstack.setData(PEPotion.toPEId(potion));
			}
		}
		return itemstack;
	}

}
