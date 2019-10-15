package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;

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
		String potion = CommonNBT.getPotionEffectType(tag);
		if (potion != null) {
			itemstack.setLegacyData(LegacyPotionId.toLegacyId(potion, isThrowablePotion));
		}
		return itemstack;
	}

}
