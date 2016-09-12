package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleSpawnPosition<T> extends ClientBoundMiddlePacket<T> {

	protected Position position;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		position = serializer.readPosition();
	}

}
