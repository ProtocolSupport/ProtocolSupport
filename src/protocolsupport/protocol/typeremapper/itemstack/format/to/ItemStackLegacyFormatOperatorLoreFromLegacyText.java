package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.protocol.typeremapper.basic.CommonNBTTransformer;
import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackNBTLegacyFormatOperator;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorLoreFromLegacyText extends ItemStackNBTLegacyFormatOperator {

	@Override
	public NBTCompound apply(String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTCompound displayTag = tag.getCompoundTagOrNull(CommonNBT.DISPLAY);
		if (displayTag != null) {
			CommonNBTTransformer.fromLegacyChatList(displayTag.getStringListTagOrNull(CommonNBT.DISPLAY_LORE));
		}
		return tag;
	}

}
