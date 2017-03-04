package protocolsupport.protocol.packet.middle.clientbound.login;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleSetCompression extends ClientBoundMiddlePacket {

	protected int threshold;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		threshold = serializer.readVarInt();
	}

}
