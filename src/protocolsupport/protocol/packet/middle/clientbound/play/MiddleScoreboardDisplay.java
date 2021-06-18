package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleScoreboardDisplay extends ClientBoundMiddlePacket {

	protected MiddleScoreboardDisplay(MiddlePacketInit init) {
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
