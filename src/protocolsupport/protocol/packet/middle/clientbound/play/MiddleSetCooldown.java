package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleSetCooldown<T> extends ClientBoundMiddlePacket<T> {

	protected int itemId;
	protected int cooldown;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		itemId = serializer.readVarInt();
		cooldown = serializer.readVarInt();
	}

}
