package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleScoreboardDisplay<T> extends ClientBoundMiddlePacket<T> {

	protected int position;
	protected String name;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		position = serializer.readUnsignedByte();
		name = serializer.readString(16);
	}

}
