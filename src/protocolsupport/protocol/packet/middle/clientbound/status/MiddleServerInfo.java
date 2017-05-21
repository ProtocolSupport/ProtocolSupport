package protocolsupport.protocol.packet.middle.clientbound.status;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.ProtocolVersionsHelper;

public abstract class MiddleServerInfo extends ClientBoundMiddlePacket {

	protected String pingJson;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		pingJson = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
	}

}
