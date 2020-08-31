package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyBanner;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;

public class BannerToLegacyComplexRemapper implements ItemStackComplexRemapper {

	protected final int color;
	public BannerToLegacyComplexRemapper(int color) {
		this.color = color;
	}

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag != null) {
			NBTCompound blockTag = tag.getCompoundTagOrNull(CommonNBT.BLOCK_TAG);
			if (blockTag != null) {
				LegacyBanner.transformBanner(blockTag);
			}
		}
		return itemstack;
	}

}
