package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddlePlayerListHeaderFooter extends ClientBoundMiddlePacket {

	protected String headerJson;
	protected String footerJson;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		headerJson = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
		footerJson = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
	}

}
