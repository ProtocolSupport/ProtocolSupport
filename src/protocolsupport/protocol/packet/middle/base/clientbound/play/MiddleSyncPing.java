package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleSyncPing extends ClientBoundMiddlePacket {

	protected MiddleSyncPing(IMiddlePacketInit init) {
		super(init);
	}

	protected int id;

	@Override
	protected void decode(ByteBuf serverdata) {
		id = serverdata.readInt();
	}

}
