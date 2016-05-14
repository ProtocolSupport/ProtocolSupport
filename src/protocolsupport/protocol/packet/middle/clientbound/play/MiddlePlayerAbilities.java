package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddlePlayerAbilities<T> extends ClientBoundMiddlePacket<T> {

	protected int flags;
	protected float flyspeed;
	protected float walkspeed;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		flags = serializer.readUnsignedByte();
		flyspeed = serializer.readFloat();
		walkspeed = serializer.readFloat();
	}

}
