package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleBlockChangeAck extends ClientBoundMiddlePacket {

	protected MiddleBlockChangeAck(IMiddlePacketInit init) {
		super(init);
	}

	protected int sequence;

	@Override
	protected void decode(ByteBuf serverdata) {
		sequence = VarNumberCodec.readVarInt(serverdata);
	}

}
