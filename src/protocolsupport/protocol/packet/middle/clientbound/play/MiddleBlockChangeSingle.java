package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;

public abstract class MiddleBlockChangeSingle extends MiddleBlock {

	protected MiddleBlockChangeSingle(MiddlePacketInit init) {
		super(init);
	}

	protected int id;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		id = VarNumberCodec.readVarInt(serverdata);
	}

}
