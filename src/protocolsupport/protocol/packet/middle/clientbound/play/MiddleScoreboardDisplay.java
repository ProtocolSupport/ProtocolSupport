package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleScoreboardDisplay extends ClientBoundMiddlePacket {

	protected int position;
	protected String name;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		position = serializer.readUnsignedByte();
		name = serializer.readString(16);
	}

}
