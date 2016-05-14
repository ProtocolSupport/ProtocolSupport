package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleWorldEvent<T> extends ClientBoundMiddlePacket<T> {

	protected int effectId;
	protected Position position;
	protected int data;
	protected boolean disableRelative;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		effectId = serializer.readInt();
		position = serializer.readPosition();
		data = serializer.readInt();
		disableRelative = serializer.readBoolean();
	}

}
