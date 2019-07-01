package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleBlockChangeSingle extends MiddleBlock {

	public MiddleBlockChangeSingle(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		id = VarNumberSerializer.readVarInt(serverdata);
	}

}
