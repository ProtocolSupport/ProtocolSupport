package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddlePlayerAbilities<T> extends ClientBoundMiddlePacket<T> {

	protected int flags;
	protected float flyspeed;
	protected float walkspeed;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		flags = serializer.readUnsignedByte();
		flyspeed = serializer.readFloat();
		walkspeed = serializer.readFloat();
	}

}
