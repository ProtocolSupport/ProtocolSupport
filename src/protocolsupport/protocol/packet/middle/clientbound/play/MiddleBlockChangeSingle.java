package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleBlockChangeSingle extends MiddleBlock {

	public MiddleBlockChangeSingle(MiddlePacketInit init) {
		super(init);
	}

	protected int id;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		id = VarNumberSerializer.readVarInt(serverdata);
	}

}
