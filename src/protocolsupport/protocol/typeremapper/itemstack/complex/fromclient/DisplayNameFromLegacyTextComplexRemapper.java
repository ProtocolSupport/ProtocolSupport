package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.typeremapper.itemstack.complex.CommonTagNames;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class DisplayNameFromLegacyTextComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType(CommonTagNames.DISPLAY, NBTTagType.COMPOUND)) {
			NBTTagCompoundWrapper displayTag = tag.getCompound(CommonTagNames.DISPLAY);
			String displayName = displayTag.getString(CommonTagNames.DISPLAY_NAME);
			if (!displayName.isEmpty()) {
				displayTag.setString(CommonTagNames.DISPLAY_NAME, ChatAPI.toJSON(new TextComponent(displayName)));
			}
		}
		return tag;
	}

}
