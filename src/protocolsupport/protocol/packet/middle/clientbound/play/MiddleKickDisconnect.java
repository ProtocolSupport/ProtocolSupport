package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleKickDisconnect extends ClientBoundMiddlePacket {

	protected String messageJson;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		messageJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
	}

}
