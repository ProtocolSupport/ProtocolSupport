package protocolsupport.protocol.typeremapper.nbt.tileupdate;

import net.minecraft.server.v1_9_R2.NBTTagCompound;
import protocolsupport.api.ProtocolVersion;

public interface SpecificTransformer {

	public NBTTagCompound transform(ProtocolVersion version, NBTTagCompound input);

}
