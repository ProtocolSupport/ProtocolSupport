package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleBlockAction<T> extends MiddleBlock<T> {

	protected int info1;
	protected int info2;
	protected int type;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		super.readFromServerData(serializer);
		info1 = serializer.readUnsignedByte();
		info2 = serializer.readUnsignedByte();
		type = serializer.readVarInt();
	}

}
