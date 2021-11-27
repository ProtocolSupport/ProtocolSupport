package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.typeremapper.legacy.LegacyBanner;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorBannerToLegacy implements ItemStackLegacyFormatOperator {

	protected final int color;

	public ItemStackLegacyFormatOperatorBannerToLegacy(int color) {
		this.color = color;
	}

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		itemstack.setLegacyData(color);
		NBTCompound rootTag = itemstack.getNBT();
		if (rootTag != null) {
			NBTCompound blockTag = rootTag.getCompoundTagOrNull(CommonNBT.BLOCK_TAG);
			if (blockTag != null) {
				LegacyBanner.transformBanner(blockTag);
			}
		}
		return itemstack;
	}

}
