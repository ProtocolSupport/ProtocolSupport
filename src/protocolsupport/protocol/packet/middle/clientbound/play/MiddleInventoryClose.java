package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public abstract class MiddleInventoryClose<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
	}

	@Override
	public void handle() {
		sharedstorage.closeWindow();
	}

}
