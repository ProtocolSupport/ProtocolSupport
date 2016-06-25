package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleHeldSlot<T> extends ClientBoundMiddlePacket<T> {

	protected int slot;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		slot = serializer.readUnsignedByte();
	}

}
