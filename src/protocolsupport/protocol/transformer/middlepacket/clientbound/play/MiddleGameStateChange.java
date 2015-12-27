package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleGameStateChange<T> extends ClientBoundMiddlePacket<T> {

	protected int type;
	protected float value;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		type = serializer.readByte();
		value = serializer.readFloat();
	}

}
