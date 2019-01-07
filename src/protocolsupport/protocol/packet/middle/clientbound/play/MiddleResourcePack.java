package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleResourcePack extends ClientBoundMiddlePacket {

	public MiddleResourcePack(ConnectionImpl connection) {
		super(connection);
	}

	protected String url;
	protected String hash;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		url = StringSerializer.readVarIntUTF8String(serverdata);
		hash = StringSerializer.readVarIntUTF8String(serverdata);
	}

}
