package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleCustomPayload extends ClientBoundMiddlePacket {

	protected String tag;
	protected ByteBuf data;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		tag = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 20);
		data = serverdata;
	}

}
