package protocolsupport.protocol.typeremapper.nbt.tileupdate;

import net.minecraft.server.v1_9_R1.NBTTagCompound;

public interface SpecificTransformer {

	public NBTTagCompound transform(NBTTagCompound input);

}
