package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleWorldSound<T> extends ClientBoundMiddlePacket<T> {

	protected int id;
	protected int category;
	protected int x;
	protected int y;
	protected int z;
	protected float volume;
	protected int pitch;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		id = serializer.readVarInt();
		category = serializer.readVarInt();
		x = serializer.readInt();
		y = serializer.readInt();
		z = serializer.readInt();
		volume = serializer.readFloat();
		pitch = serializer.readUnsignedByte();
	}

}
