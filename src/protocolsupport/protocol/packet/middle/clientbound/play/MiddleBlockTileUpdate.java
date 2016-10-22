package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;

public abstract class MiddleBlockTileUpdate<T> extends MiddleBlock<T> {

	protected int type;
	protected NBTTagCompoundWrapper tag;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		super.readFromServerData(serializer);
		type = serializer.readUnsignedByte();
		tag = serializer.readTag();
	}

}
