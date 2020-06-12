package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

//TODO: Enum for status id?
public abstract class MiddleEntityStatus extends ClientBoundMiddlePacket {

	public MiddleEntityStatus(ConnectionImpl connection) {
		super(connection);
	}

	protected static final int STATUS_LIVING_DEATH = 3;

	protected int entityId;
	protected int status;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		entityId = serverdata.readInt();
		status = serverdata.readUnsignedByte();
	}

}
