package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.utils.CommonNBT;

public class DisplayNameToLegacyTextComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTCompound displayTag = tag.getCompoundTagOrNull(CommonNBT.DISPLAY);
		if (displayTag != null) {
			NBTString displayNameTag = displayTag.getStringTagOrNull(CommonNBT.DISPLAY_NAME);
			if (displayNameTag != null) {
				displayTag.setTag(CommonNBT.DISPLAY_NAME, new NBTString(ChatAPI.fromJSON(displayNameTag.getValue(), true).toLegacyText(locale)));
			}
		}
		return tag;
	}

}
