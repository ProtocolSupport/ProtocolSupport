package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public abstract class MiddleChat<T> extends ClientBoundMiddlePacket<T> {

	protected String chatJson;
	protected byte position;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		chatJson = serializer.readString(Short.MAX_VALUE);
		position = serializer.readByte();
	}

}
