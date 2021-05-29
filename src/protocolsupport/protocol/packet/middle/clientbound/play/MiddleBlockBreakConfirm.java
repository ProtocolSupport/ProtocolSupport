package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

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
		blockId = VarNumberSerializer.readVarInt(serverdata);
		status = VarNumberSerializer.readVarInt(serverdata);
		successful = serverdata.readBoolean();
	}

}
