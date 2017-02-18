package protocolsupport.protocol.typeremapper.itemstack.clientbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackNBTSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class PlayerSkullSpecificRemapper extends ItemStackNBTSpecificRemapper {

	@Override
	public NBTTagCompoundWrapper remapTag(ProtocolVersion version, ItemStackWrapper itemstack, NBTTagCompoundWrapper tag) {
		ItemStackRemapper.remapSkull(tag);
		return tag;
	}

}
