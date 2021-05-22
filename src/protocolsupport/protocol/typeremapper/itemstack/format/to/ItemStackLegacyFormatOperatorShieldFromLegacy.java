package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackLegacyFormatOperator;
import protocolsupport.protocol.typeremapper.legacy.LegacyBanner;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorShieldFromLegacy implements ItemStackLegacyFormatOperator {

	@Override
	public NetworkItemStack apply(String locale, NetworkItemStack itemstack) {
		NBTCompound rootTag = itemstack.getNBT();
		if (rootTag != null) {
			NBTCompound blockTag = rootTag.getCompoundTagOrNull(CommonNBT.BLOCK_TAG);
			if (blockTag != null) {
				NBTNumber base = blockTag.getNumberTagOrNull(CommonNBT.BANNER_BASE);
				if (base != null) {
					blockTag.setTag(CommonNBT.BANNER_BASE, new NBTInt(15 - base.getAsInt()));
				}
				LegacyBanner.transformBanner(blockTag);
			}
		}
		return itemstack;
	}

}
