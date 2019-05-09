package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;

public class LoreToLegacyTextComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTCompound displayTag = tag.getTagOfType(CommonNBT.DISPLAY, NBTType.COMPOUND);
		if (displayTag != null) {
			NBTList<NBTString> loreTag = displayTag.getTagListOfType(CommonNBT.DISPLAY_LORE, NBTType.STRING);
			if (loreTag != null) {
				NBTList<NBTString> legacyLoreTag = new NBTList<>(NBTType.STRING);
				for (NBTString modernTag : loreTag.getTags()) {
					legacyLoreTag.addTag(new NBTString(ChatAPI.fromJSON(modernTag.getValue()).toLegacyText(locale)));
				}
				displayTag.setTag(CommonNBT.DISPLAY_LORE, legacyLoreTag);
			}
		}
		return tag;
	}

}
