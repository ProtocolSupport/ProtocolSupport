package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleResourcePack extends ClientBoundMiddlePacket {

	protected String url;
	protected String hash;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		url = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
		hash = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
	}

}
