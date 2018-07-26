package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.typeremapper.itemstack.complex.CommonTagNames;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class DisplayNameToLegacyTextComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType(CommonTagNames.DISPLAY, NBTTagType.COMPOUND)) {
			NBTTagCompoundWrapper displayTag = tag.getCompound(CommonTagNames.DISPLAY);
			String displayName = displayTag.getString(CommonTagNames.DISPLAY_NAME);
			if (!displayName.isEmpty()) {
				displayTag.setString(CommonTagNames.DISPLAY_NAME, ChatAPI.fromJSON(displayName).toLegacyText(locale));
			}
		}
		return tag;
	}

}
