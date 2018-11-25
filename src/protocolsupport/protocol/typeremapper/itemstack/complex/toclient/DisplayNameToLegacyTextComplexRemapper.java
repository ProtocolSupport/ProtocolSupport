package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class DisplayNameToLegacyTextComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTCompound displayTag = tag.getTagOfType(CommonNBT.DISPLAY, NBTType.COMPOUND);
		if (displayTag != null) {
			NBTString displayNameTag = displayTag.getTagOfType(CommonNBT.DISPLAY_NAME, NBTType.STRING);
			if (displayNameTag != null) {
				String converted;
				try {
					converted = ChatAPI.fromJSON(displayNameTag.getValue()).toLegacyText(locale);
				} catch (ChatAPI.JsonParseException e) {
					converted = displayNameTag.getValue();
				}
				displayTag.setTag(CommonNBT.DISPLAY_NAME, new NBTString(converted));
			}
		}
		return tag;
	}

}
