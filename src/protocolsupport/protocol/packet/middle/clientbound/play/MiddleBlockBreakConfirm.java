package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleBlockBreakConfirm extends MiddleBlock {

	public MiddleBlockBreakConfirm(ConnectionImpl connection) {
		super(connection);
	}

	protected int blockId;
	protected int status;
	protected boolean successful;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		blockId = VarNumberSerializer.readVarInt(serverdata);
		status = VarNumberSerializer.readVarInt(serverdata);
		successful = serverdata.readBoolean();
	}

}
