package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;

public abstract class MiddleBlockBreakConfirm extends MiddleBlock {

	protected MiddleBlockBreakConfirm(MiddlePacketInit init) {
		super(init);
	}

	protected int blockId;
	protected int status;
	protected boolean successful;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		blockId = VarNumberCodec.readVarInt(serverdata);
		status = VarNumberCodec.readVarInt(serverdata);
		successful = serverdata.readBoolean();
	}

}
