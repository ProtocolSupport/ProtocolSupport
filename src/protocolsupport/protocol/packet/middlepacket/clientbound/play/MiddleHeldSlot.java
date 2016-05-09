package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleHeldSlot<T> extends ClientBoundMiddlePacket<T> {

	protected int slot;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		slot = serializer.readUnsignedByte();
	}

}
