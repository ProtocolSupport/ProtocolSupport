package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleWorldSound<T> extends ClientBoundMiddlePacket<T> {

	protected String name;
	protected int x;
	protected int y;
	protected int z;
	protected float volume;
	protected int pitch;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		name = serializer.readString(Short.MAX_VALUE);
		x = serializer.readInt();
		y = serializer.readInt();
		z = serializer.readInt();
		volume = serializer.readFloat();
		pitch = serializer.readUnsignedByte();
	}

}
