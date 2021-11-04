package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleStopSound extends ClientBoundMiddlePacket {

	protected static final int FLAG_SOURCE = 0x1;
	protected static final int FLAG_NAME = 0x2;

	protected MiddleStopSound(IMiddlePacketInit init) {
		super(init);
	}

	protected int source = -1;
	protected String name;

	@Override
	protected void decode(ByteBuf serverdata) {
		int flags = serverdata.readByte();
		source = (flags & FLAG_SOURCE) == FLAG_SOURCE ? VarNumberCodec.readVarInt(serverdata) : -1;
		name = (flags & FLAG_NAME) == FLAG_NAME ? StringCodec.readVarIntUTF8String(serverdata) : null;
	}

}
