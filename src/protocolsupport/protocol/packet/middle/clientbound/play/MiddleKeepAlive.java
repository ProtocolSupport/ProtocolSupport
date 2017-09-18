package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleKeepAlive extends ClientBoundMiddlePacket {

	protected long keepAliveId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		keepAliveId = serverdata.readLong();
	}

}
