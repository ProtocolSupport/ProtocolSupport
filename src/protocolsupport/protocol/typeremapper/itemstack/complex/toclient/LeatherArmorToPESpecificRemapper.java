package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;
import protocolsupport.protocol.utils.types.nbt.NBTNumber;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class LeatherArmorToPESpecificRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTCompound remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTCompound tag) {
		NBTCompound display = tag.getTagOfType("display", NBTType.COMPOUND);
		if (display != null) {
			NBTNumber color = display.getNumberTag("color");
			if (color != null) {
				tag.setTag("customColor", new NBTInt(color.getAsInt()));
				display.removeTag("color");
			}
		}
		return tag;
	}

}
