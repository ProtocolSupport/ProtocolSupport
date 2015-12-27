package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleInventoryConfirmTransaction<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;
	protected int actionNumber;
	protected boolean accepted;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
		actionNumber = serializer.readShort();
		accepted = serializer.readBoolean();
	}

}
