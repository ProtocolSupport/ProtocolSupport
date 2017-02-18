package protocolsupport.protocol.typeremapper.nbt;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

@FunctionalInterface
public interface NBTSpecificRemapper {

	public NBTTagCompoundWrapper remap(ProtocolVersion version, NBTTagCompoundWrapper input);

}
