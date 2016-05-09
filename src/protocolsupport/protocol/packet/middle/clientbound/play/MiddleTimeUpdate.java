package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleTimeUpdate<T> extends ClientBoundMiddlePacket<T> {

	protected long worldAge;
	protected long timeOfDay;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		worldAge = serializer.readLong();
		timeOfDay = serializer.readLong();
	}

	@Override
	public void handle() {
	}

}
