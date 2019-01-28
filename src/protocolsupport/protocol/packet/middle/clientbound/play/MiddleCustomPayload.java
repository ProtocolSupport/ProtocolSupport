package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleCustomPayload extends ClientBoundMiddlePacket {

	public MiddleCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	protected String tag;
	protected ByteBuf data;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		tag = StringSerializer.readVarIntUTF8String(serverdata);
		data = serverdata.readSlice(serverdata.readableBytes());
	}

}
