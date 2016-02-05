package protocolsupport.protocol.typeremapper.nbt.tileupdate;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public interface SpecificTransformer {

	public NBTTagCompound transform(NBTTagCompound input);

}
