package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleAdvancementsTab extends ClientBoundMiddlePacket {

	protected MiddleAdvancementsTab(IMiddlePacketInit init) {
		super(init);
	}

	protected String identifier;

	@Override
	protected void decode(ByteBuf serverdata) {
		if (serverdata.readBoolean()) {
			identifier = StringCodec.readVarIntUTF8String(serverdata);
		} else {
			identifier = null;
		}
	}

}
