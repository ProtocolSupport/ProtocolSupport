package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleInventoryClose<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		windowId = serializer.readUnsignedByte();
	}

	@Override
	public void handle() {
		cache.closeWindow();
	}

}
