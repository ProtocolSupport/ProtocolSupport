package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleAcknowledgePlayerDigging extends MiddleBlock {

	public MiddleAcknowledgePlayerDigging(ConnectionImpl connection) {
		super(connection);
	}

	protected int blockId;
	protected int status;
	protected boolean successful;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		blockId = VarNumberSerializer.readVarInt(serverdata);
		status = VarNumberSerializer.readVarInt(serverdata);
		successful = serverdata.readBoolean();
	}

}
