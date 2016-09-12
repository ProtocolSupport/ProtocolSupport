package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleGameStateChange<T> extends ClientBoundMiddlePacket<T> {

	protected int type;
	protected float value;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		type = serializer.readByte();
		value = serializer.readFloat();
	}

}
