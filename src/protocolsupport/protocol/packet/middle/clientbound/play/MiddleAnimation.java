package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleAnimation extends ClientBoundMiddlePacket {

	public MiddleAnimation(ConnectionImpl connection) {
		super(connection);
	}

	protected int entityId;
	protected int animation;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		animation = serverdata.readUnsignedByte();
	}

}
