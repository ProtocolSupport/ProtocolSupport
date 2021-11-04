package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;

public abstract class MiddleBlockBreakAnimation extends MiddleBlock {

	protected MiddleBlockBreakAnimation(IMiddlePacketInit init) {
		super(init);
	}

	protected int entityId;
	protected int stage;

	@Override
	protected void decode(ByteBuf serverdata) {
		entityId = VarNumberCodec.readVarInt(serverdata);
		super.decode(serverdata);
		stage = serverdata.readByte();
	}

}
