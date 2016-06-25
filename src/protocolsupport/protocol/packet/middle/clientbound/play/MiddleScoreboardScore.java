package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleScoreboardScore<T> extends ClientBoundMiddlePacket<T> {

	protected String name;
	protected int mode;
	protected String objectiveName;
	protected int value;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		name = serializer.readString(40);
		mode = serializer.readUnsignedByte();
		objectiveName = serializer.readString(16);
		if (mode != 1) {
			value = serializer.readVarInt();
		}
	}

}
