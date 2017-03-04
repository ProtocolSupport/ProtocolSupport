package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleResourcePack extends ClientBoundMiddlePacket {

	protected String url;
	protected String hash;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		url = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
		hash = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
	}

}
