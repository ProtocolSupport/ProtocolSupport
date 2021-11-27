package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorPotionToLegacyId implements ItemStackLegacyFormatOperator {

	protected final boolean isThrowablePotion;

	public ItemStackLegacyFormatOperatorPotionToLegacyId(boolean isThrowablePotion) {
		this.isThrowablePotion = isThrowablePotion;
	}

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		NBTCompound rootTag = itemstack.getNBT();
		if (rootTag == null) {
			return itemstack;
		}
		String potion = CommonNBT.getPotionEffectType(rootTag);
		if (potion != null) {
			itemstack.setLegacyData(LegacyPotionId.toLegacyId(potion, isThrowablePotion));
		}
		return itemstack;
	}

}
