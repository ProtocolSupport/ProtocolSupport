package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;

public class DisplayNameFromLegacyTextComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTCompound displayTag = tag.getTagOfType(CommonNBT.DISPLAY, NBTType.COMPOUND);
		if (displayTag != null) {
			NBTString displayNameTag = displayTag.getTagOfType(CommonNBT.DISPLAY_NAME, NBTType.STRING);
			if (displayNameTag != null) {
				displayTag.setTag(CommonNBT.DISPLAY_NAME, new NBTString(ChatAPI.toJSON(new TextComponent(displayNameTag.getValue()))));
			}
		}
		return tag;
	}

}
