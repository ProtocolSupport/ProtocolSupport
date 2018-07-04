package protocolsupport.protocol.typeremapper.itemstack.fromclient;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class LeatherArmorFromPERemapper extends ItemStackNBTSpecificRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, String locale, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		if (tag.hasKeyOfType("customColor", NBTTagType.INT)) {
			NBTTagCompoundWrapper display = tag.hasKeyOfType("display", NBTTagType.COMPOUND) ? tag.getCompound("display") : ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
			display.setInt("color", tag.getIntNumber("customColor"));
			tag.remove("customColor");
		}
		return tag;
	}

}
