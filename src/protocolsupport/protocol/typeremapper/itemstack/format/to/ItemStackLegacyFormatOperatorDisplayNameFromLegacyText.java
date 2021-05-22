package protocolsupport.protocol.typeremapper.itemstack.format.to;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.typeremapper.itemstack.format.ItemStackNBTLegacyFormatOperator;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.CommonNBT;

public class ItemStackLegacyFormatOperatorDisplayNameFromLegacyText extends ItemStackNBTLegacyFormatOperator {

	@Override
	public NBTCompound apply(String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTCompound displayTag = tag.getCompoundTagOrNull(CommonNBT.DISPLAY);
		if (displayTag != null) {
			NBTString displayNameTag = displayTag.getStringTagOrNull(CommonNBT.DISPLAY_NAME);
			if (displayNameTag != null) {
				displayTag.setTag(CommonNBT.DISPLAY_NAME, new NBTString(ChatAPI.toJSON(new TextComponent(displayNameTag.getValue()))));
			}
		}
		return tag;
	}

}
