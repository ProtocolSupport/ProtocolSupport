package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;

public abstract class MiddleBlockTileUpdate<T> extends MiddleBlock<T> {

	protected int type;
	protected NBTTagCompoundWrapper tag;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		type = serializer.readUnsignedByte();
		tag = serializer.readTag();
	}

}
