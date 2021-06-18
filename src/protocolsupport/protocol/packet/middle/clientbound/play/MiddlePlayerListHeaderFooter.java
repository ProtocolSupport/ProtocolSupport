package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddlePlayerListHeaderFooter extends ClientBoundMiddlePacket {

	protected MiddlePlayerListHeaderFooter(MiddlePacketInit init) {
		super(init);
	}

	protected String headerJson;
	protected String footerJson;

	@Override
	protected void decode(ByteBuf serverdata) {
		headerJson = StringCodec.readVarIntUTF8String(serverdata);
		footerJson = StringCodec.readVarIntUTF8String(serverdata);
	}

}
