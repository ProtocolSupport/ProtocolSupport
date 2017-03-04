package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleBlockAction extends MiddleBlock {

	protected int info1;
	protected int info2;
	protected int type;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		info1 = serverdata.readUnsignedByte();
		info2 = serverdata.readUnsignedByte();
		type = VarNumberSerializer.readVarInt(serverdata);
	}

}
