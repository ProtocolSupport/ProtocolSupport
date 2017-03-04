package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleEntityStatus extends ClientBoundMiddlePacket {

	protected int entityId;
	protected int status;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = serverdata.readInt();
		status = serverdata.readUnsignedByte();
	}

}
