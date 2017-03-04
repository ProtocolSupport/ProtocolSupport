package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleKeepAlive extends ClientBoundMiddlePacket {

	protected int keepAliveId;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		keepAliveId = serializer.readVarInt();
	}

}
