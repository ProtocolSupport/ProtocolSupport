package protocolsupport.protocol.typeremapper.nbt.tileupdate;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;

public interface SpecificTransformer {

	public NBTTagCompoundWrapper transform(ProtocolVersion version, NBTTagCompoundWrapper input);

}
