package protocolsupport.protocol.typeremapper.itemstack.complex.toclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class LeatherArmorToPESpecificRemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType("display", NBTTagType.COMPOUND)) {
			NBTTagCompoundWrapper display = tag.getCompound("display");
			if (display.hasKeyOfType("color", NBTTagType.INT)) {
				tag.setInt("customColor", display.getIntNumber("color"));
				display.remove("color");
			}
		}
		return tag;
	}

}
