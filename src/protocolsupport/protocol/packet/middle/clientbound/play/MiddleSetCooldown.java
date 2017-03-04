package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleSetCooldown extends ClientBoundMiddlePacket {

	protected int itemId;
	protected int cooldown;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		itemId = serializer.readVarInt();
		cooldown = serializer.readVarInt();
	}

}
