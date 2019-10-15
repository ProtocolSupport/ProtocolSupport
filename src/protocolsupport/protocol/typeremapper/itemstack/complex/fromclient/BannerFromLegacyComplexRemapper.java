package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyBanner;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;

public class BannerFromLegacyComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		itemstack.setTypeId(LegacyBanner.getMaterialByColor(itemstack.getLegacyData()));
		NBTCompound tag = itemstack.getNBT();
		if (tag != null) {
			NBTCompound blockTag = tag.getTagOfType(CommonNBT.BLOCK_TAG, NBTType.COMPOUND);
			if (blockTag != null) {
				LegacyBanner.transformBanner(blockTag);
			}
		}
		return itemstack;
	}

}
