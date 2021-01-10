package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleBlockAction extends MiddleBlock {

	public MiddleBlockAction(MiddlePacketInit init) {
		super(init);
	}

	protected int actionId;
	protected int actionParam;
	protected int blockId;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		actionId = serverdata.readUnsignedByte();
		actionParam = serverdata.readUnsignedByte();
		blockId = VarNumberSerializer.readVarInt(serverdata);
	}

}
