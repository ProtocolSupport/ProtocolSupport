package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import net.minecraft.server.v1_9_R1.NBTTagCompound;
import protocolsupport.protocol.PacketDataSerializer;

public abstract class MiddleBlockTileUpdate<T> extends MiddleBlock<T> {

	protected int type;
	protected NBTTagCompound tag;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		type = serializer.readUnsignedByte();
		tag = serializer.readTag();
	}

}
