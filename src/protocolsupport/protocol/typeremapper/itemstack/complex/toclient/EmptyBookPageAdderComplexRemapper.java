package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTList;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class EmptyBookPageAdderComplexRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		if (tag.getTagListOfType("pages", NBTType.STRING) == null) {
			NBTList<NBTString> pages = new NBTList<>(NBTType.STRING);
			pages.addTag(new NBTString(""));
			tag.setTag("pages", pages);
		}
		return tag;
	}

}
