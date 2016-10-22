package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleServerDifficulty<T> extends ClientBoundMiddlePacket<T> {

	protected int difficulty;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		difficulty = serializer.readByte();
	}

}
