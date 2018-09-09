package protocolsupport.protocol.typeremapper.itemstack.complex.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackNBTComplexRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public class LeatherArmorFromPERemapper extends ItemStackNBTComplexRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, NetworkItemStack itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType("customColor", NBTTagType.INT)) {
			NBTTagCompoundWrapper display = tag.hasKeyOfType("display", NBTTagType.COMPOUND) ? tag.getCompound("display") : ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			display.setInt("color", tag.getIntNumber("customColor"));
			tag.remove("customColor");
		}
		return tag;
	}

}
