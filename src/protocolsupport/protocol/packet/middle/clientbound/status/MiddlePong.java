package protocolsupport.protocol.packet.middle.clientbound.status;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddlePong extends ClientBoundMiddlePacket {

	protected long pingId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		pingId = serverdata.readLong();
	}

}
