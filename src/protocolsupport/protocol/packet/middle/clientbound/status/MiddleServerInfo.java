package protocolsupport.protocol.packet.middle.clientbound.status;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.pingresponse.PingResponse;

public abstract class MiddleServerInfo extends ClientBoundMiddlePacket {

	public MiddleServerInfo(ConnectionImpl connection) {
		super(connection);
	}

	protected PingResponse ping;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		ping = PingResponse.fromJson(StringSerializer.readVarIntUTF8String(serverdata));
	}

}
