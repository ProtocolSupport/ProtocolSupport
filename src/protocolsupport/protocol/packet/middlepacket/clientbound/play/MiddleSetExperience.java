package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleSetExperience<T> extends ClientBoundMiddlePacket<T> {

	protected float exp;
	protected int level;
	protected int totalExp;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		exp = serializer.readFloat();
		level = serializer.readVarInt();
		totalExp = serializer.readVarInt();
	}

}
