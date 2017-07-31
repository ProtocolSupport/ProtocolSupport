package protocolsupport.protocol.packet.middle.clientbound.status;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.pingresponse.PingResponse;

public abstract class MiddleServerInfo extends ClientBoundMiddlePacket {

	protected PingResponse ping;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		ping = PingResponse.fromJson(StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC));
	}

}
