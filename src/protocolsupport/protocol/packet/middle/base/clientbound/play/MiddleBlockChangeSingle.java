package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;

public abstract class MiddleBlockChangeSingle extends MiddleBlock {

	protected MiddleBlockChangeSingle(IMiddlePacketInit init) {
		super(init);
	}

	protected int blockdata;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		blockdata = VarNumberCodec.readVarInt(serverdata);
	}

}
