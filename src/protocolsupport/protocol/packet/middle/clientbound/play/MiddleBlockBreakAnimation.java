package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleBlockBreakAnimation extends MiddleBlock {

	public MiddleBlockBreakAnimation(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected int stage;

	@Override
	public void readServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		super.readServerData(serverdata);
		stage = serverdata.readByte();
	}

}
