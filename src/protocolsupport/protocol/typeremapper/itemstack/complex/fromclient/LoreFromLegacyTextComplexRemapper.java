package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;

public class LoreFromLegacyTextComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTCompound displayTag = tag.getTagOfType(CommonNBT.DISPLAY, NBTType.COMPOUND);
		if (displayTag != null) {
			NBTList<NBTString> loreTag = displayTag.getTagListOfType(CommonNBT.DISPLAY_LORE, NBTType.STRING);
			if (loreTag != null) {
				NBTList<NBTString> modernLoreTag = new NBTList<>(NBTType.STRING);
				for (NBTString legacyTag : loreTag.getTags()) {
					modernLoreTag.addTag(new NBTString(ChatAPI.toJSON(new TextComponent(legacyTag.getValue()))));
				}
				displayTag.setTag(CommonNBT.DISPLAY_LORE, modernLoreTag);
			}
		}
		return tag;
	}

}
