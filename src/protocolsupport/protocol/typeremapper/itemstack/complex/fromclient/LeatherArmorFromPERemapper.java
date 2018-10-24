package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;
import protocolsupport.protocol.utils.types.nbt.NBTNumber;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class LeatherArmorFromPERemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTNumber customColor = tag.getNumberTag("customColor");
		if (customColor != null) {
			NBTCompound display = tag.getTagOfType("display", NBTType.COMPOUND);
			if (display == null) {
				display = new NBTCompound();
			}
			display.setTag("color", new NBTInt(customColor.getAsInt()));
			tag.setTag("display", display);
			tag.removeTag("customColor");
		}
		return tag;
	}

}
