package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleCustomPayload extends ClientBoundMiddlePacket {

	protected String tag;
	protected ByteBuf data;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		tag = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 20);
		data = serverdata;
	}

}
