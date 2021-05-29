package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddlePlayerListHeaderFooter extends ClientBoundMiddlePacket {

	protected MiddlePlayerListHeaderFooter(MiddlePacketInit init) {
		super(init);
	}

	protected String headerJson;
	protected String footerJson;

	@Override
	protected void decode(ByteBuf serverdata) {
		headerJson = StringSerializer.readVarIntUTF8String(serverdata);
		footerJson = StringSerializer.readVarIntUTF8String(serverdata);
	}

}
