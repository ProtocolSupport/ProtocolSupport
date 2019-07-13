package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;

public class BookPagesFromPERemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTList<NBTCompound> pages = tag.getTagListOfType("pages", NBTType.COMPOUND);
		if (pages != null) {
			NBTList<NBTString> newpages = new NBTList<>(NBTType.STRING);
			for (int i = 0; i < pages.size(); i++) {
				newpages.addTag(new NBTString(pages.getTag(i).getTagOfType("text", NBTType.STRING).getValue()));
			}
			tag.setTag("pages", newpages);
		}
		return tag;
	}

}
