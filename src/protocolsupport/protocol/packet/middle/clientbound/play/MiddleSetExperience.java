package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleSetExperience extends ClientBoundMiddlePacket {

	protected float exp;
	protected int level;
	protected int totalExp;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		exp = serializer.readFloat();
		level = serializer.readVarInt();
		totalExp = serializer.readVarInt();
	}

}
