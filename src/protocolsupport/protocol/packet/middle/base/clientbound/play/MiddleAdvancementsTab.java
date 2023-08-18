package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleAdvancementsTab extends ClientBoundMiddlePacket {

	protected MiddleAdvancementsTab(IMiddlePacketInit init) {
		super(init);
	}

	protected String identifier;

	@Override
	protected void decode(ByteBuf serverdata) {
		identifier = OptionalCodec.readOptionalVarIntUTF8String(serverdata);
	}

}
