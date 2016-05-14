package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleInventoryConfirmTransaction<T> extends ClientBoundMiddlePacket<T> {

	protected int windowId;
	protected int actionNumber;
	protected boolean accepted;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
		actionNumber = serializer.readShort();
		accepted = serializer.readBoolean();
	}

}
