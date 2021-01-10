package protocolsupport.protocol.packet.middle.clientbound.status;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddlePong extends ClientBoundMiddlePacket {

	public MiddlePong(MiddlePacketInit init) {
		super(init);
	}

	protected long pingId;

	@Override
	protected void decode(ByteBuf serverdata) {
		pingId = serverdata.readLong();
	}

}
