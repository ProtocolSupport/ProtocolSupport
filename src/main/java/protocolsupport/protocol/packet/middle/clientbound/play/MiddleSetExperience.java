package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleSetExperience<T> extends ClientBoundMiddlePacket<T> {

	protected float exp;
	protected int level;
	protected int totalExp;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		exp = serializer.readFloat();
		level = serializer.readVarInt();
		totalExp = serializer.readVarInt();
	}

}
