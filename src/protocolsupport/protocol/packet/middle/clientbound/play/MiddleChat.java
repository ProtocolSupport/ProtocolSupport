package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleChat extends ClientBoundMiddlePacket {

	protected String chatJson;
	protected byte position;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chatJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
		position = serverdata.readByte();
	}

}
