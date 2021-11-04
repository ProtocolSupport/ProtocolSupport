package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddlePlayerListHeaderFooter extends ClientBoundMiddlePacket {

	protected MiddlePlayerListHeaderFooter(IMiddlePacketInit init) {
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
