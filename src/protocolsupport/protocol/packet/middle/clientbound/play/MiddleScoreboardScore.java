package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleScoreboardScore extends ClientBoundMiddlePacket {

	protected String name;
	protected int mode;
	protected String objectiveName;
	protected int value;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		name = serializer.readString(40);
		mode = serializer.readUnsignedByte();
		objectiveName = serializer.readString(16);
		if (mode != 1) {
			value = serializer.readVarInt();
		}
	}

}
