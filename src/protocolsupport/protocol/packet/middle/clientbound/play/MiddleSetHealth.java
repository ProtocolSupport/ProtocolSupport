package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleSetHealth extends ClientBoundMiddlePacket {

	protected float health;
	protected int food;
	protected float saturation;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		health = serializer.readFloat();
		food = serializer.readVarInt();
		saturation = serializer.readFloat();
	}

}
