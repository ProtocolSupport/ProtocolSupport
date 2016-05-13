package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public abstract class MiddleHeldSlot<T> extends ClientBoundMiddlePacket<T> {

	protected int slot;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		slot = serializer.readUnsignedByte();
	}

}
