package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyBanner;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;

public class ShieldFromLegacyComplexRemapper implements ItemStackComplexRemapper {

	@Override
	public NetworkItemStack remap(ProtocolVersion version, String locale, NetworkItemStack itemstack) {
		NBTCompound tag = itemstack.getNBT();
		if (tag != null) {
			NBTCompound blockTag = tag.getTagOfType(CommonNBT.BLOCK_TAG, NBTType.COMPOUND);
			if (blockTag != null) {
				NBTNumber base = blockTag.getNumberTag(CommonNBT.BANNER_BASE);
				if (base != null) {
					blockTag.setTag(CommonNBT.BANNER_BASE, new NBTInt(15 - base.getAsInt()));
				}
				LegacyBanner.transformBanner(blockTag);
			}
		}
		return itemstack;
	}

}
