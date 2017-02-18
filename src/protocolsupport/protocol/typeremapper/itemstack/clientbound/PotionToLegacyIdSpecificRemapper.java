package protocolsupport.protocol.typeremapper.itemstack.clientbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyPotion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.protocol.utils.data.PotionData;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class PotionToLegacyIdSpecificRemapper extends ItemStackNBTSpecificRemapper {

	private final boolean isThrowablePotion;
	public PotionToLegacyIdSpecificRemapper(boolean isThrowablePotion) {
		this.isThrowablePotion = isThrowablePotion;
	}

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		String potion = tag.getString("Potion");
		if (!potion.isEmpty()) {
			NBTTagListWrapper tagList = tag.getList("CustomPotionEffects", NBTTagCompoundWrapper.TYPE_COMPOUND);
			if (tagList.size() >= 1) {
				potion = PotionData.getNameById(tagList.getCompound(0).getNumber("Id"));
			}
			Integer data = LegacyPotion.toLegacyId(potion, isThrowablePotion);
			itemstack.setData(data);
			String basicTypeName = LegacyPotion.getBasicTypeName(potion);
			if (basicTypeName != null) {
				itemstack.setDisplayName(basicTypeName);
			}
		}
		return tag;
	}

}
