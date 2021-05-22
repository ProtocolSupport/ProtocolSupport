package protocolsupport.protocol.typeremapper.itemstack.format.from;

import protocolsupport.protocol.typeremapper.basic.CommonNBTTransformer;
import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackNBTLegacyFormatOperator;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorLoreToLegacyText extends ItemStackNBTLegacyFormatOperator {

	@Override
	public NBTCompound apply(String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTCompound displayTag = tag.getCompoundTagOrNull(CommonNBT.DISPLAY);
		if (displayTag != null) {
			CommonNBTTransformer.toLegacyChatList(displayTag.getStringListTagOrNull(CommonNBT.DISPLAY_LORE), locale);
		}
		return tag;
	}

}
