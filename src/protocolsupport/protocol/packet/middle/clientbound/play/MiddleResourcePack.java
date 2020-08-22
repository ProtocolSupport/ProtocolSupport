package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleResourcePack extends ClientBoundMiddlePacket {

	public MiddleResourcePack(MiddlePacketInit init) {
		super(init);
	}

	protected String url;
	protected String hash;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		url = StringSerializer.readVarIntUTF8String(serverdata);
		hash = StringSerializer.readVarIntUTF8String(serverdata);
	}

}
