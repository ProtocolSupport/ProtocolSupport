package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleBlockBreakAnimation extends MiddleBlock {

	protected MiddleBlockBreakAnimation(MiddlePacketInit init) {
		super(init);
	}

	protected int entityId;
	protected int stage;

	@Override
	protected void decode(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		super.decode(serverdata);
		stage = serverdata.readByte();
	}

}
