package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.typeremapper.legacy.LegacyBanner;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorBannerFromLegacy implements ItemStackLegacyFormatOperator {

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		itemstack.setTypeId(LegacyBanner.getMaterialByColor(itemstack.getLegacyData()));
		NBTCompound rooTag = itemstack.getNBT();
		if (rooTag != null) {
			NBTCompound blockTag = rooTag.getCompoundTagOrNull(CommonNBT.BLOCK_TAG);
			if (blockTag != null) {
				LegacyBanner.transformBanner(blockTag);
			}
		}
		return itemstack;
	}

}
