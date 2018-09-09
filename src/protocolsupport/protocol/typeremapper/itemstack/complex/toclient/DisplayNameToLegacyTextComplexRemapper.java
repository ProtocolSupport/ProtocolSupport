package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class DisplayNameToLegacyTextComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType(CommonNBT.DISPLAY, NBTTagType.COMPOUND)) {
			NBTTagCompoundWrapper displayTag = tag.getCompound(CommonNBT.DISPLAY);
			String displayName = displayTag.getString(CommonNBT.DISPLAY_NAME);
			if (!displayName.isEmpty()) {
				displayTag.setString(CommonNBT.DISPLAY_NAME, ChatAPI.fromJSON(displayName).toLegacyText(locale));
			}
		}
		return tag;
	}

}
