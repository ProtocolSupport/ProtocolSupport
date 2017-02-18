package protocolsupport.protocol.typeremapper.tileentity;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

@FunctionalInterface
public interface TileEntitySpecificRemapper {

	public NBTTagCompoundWrapper remap(ProtocolVersion version, NBTTagCompoundWrapper input);

}
