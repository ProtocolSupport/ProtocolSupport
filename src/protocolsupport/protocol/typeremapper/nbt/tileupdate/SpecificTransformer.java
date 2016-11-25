package protocolsupport.protocol.typeremapper.nbt.tileupdate;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.nms.NBTTagCompoundWrapper;

@FunctionalInterface
public interface SpecificTransformer {

	public NBTTagCompoundWrapper transform(ProtocolVersion version, NBTTagCompoundWrapper input);

}
