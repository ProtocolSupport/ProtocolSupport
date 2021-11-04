package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleScoreboardDisplay extends ClientBoundMiddlePacket {

	protected MiddleScoreboardDisplay(IMiddlePacketInit init) {
		super(init);
	}

	protected int position;
	protected String name;

	@Override
	protected void decode(ByteBuf serverdata) {
		position = serverdata.readUnsignedByte();
		name = StringCodec.readVarIntUTF8String(serverdata);
	}

}
