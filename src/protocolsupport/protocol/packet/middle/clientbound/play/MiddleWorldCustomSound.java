package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleWorldCustomSound<T> extends ClientBoundMiddlePacket<T> {

	protected String id;
	protected int category;
	protected int x;
	protected int y;
	protected int z;
	protected float volume;
	protected float pitch;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		id = serializer.readString();
		category = serializer.readVarInt();
		x = serializer.readInt();
		y = serializer.readInt();
		z = serializer.readInt();
		volume = serializer.readFloat();
		pitch = serializer.readFloat();
	}

}
