package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleLoginDisconnect extends ClientBoundMiddlePacket {

	protected String messageJson;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		messageJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
	}

}
