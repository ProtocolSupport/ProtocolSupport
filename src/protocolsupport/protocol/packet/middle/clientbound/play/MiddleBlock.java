package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.Position;

public abstract class MiddleBlock extends ClientBoundMiddlePacket {

	protected Position position;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		position = serializer.readPosition();
	}

}
