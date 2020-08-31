package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.basic.CommonNBTTransformer;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;

public class LoreToLegacyTextComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTCompound displayTag = tag.getCompoundTagOrNull(CommonNBT.DISPLAY);
		if (displayTag != null) {
			CommonNBTTransformer.toLegacyChatList(displayTag.getStringListTagOrNull(CommonNBT.DISPLAY_LORE), locale);
		}
		return tag;
	}

}
