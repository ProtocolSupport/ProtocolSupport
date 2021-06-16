package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleSyncPing extends ClientBoundMiddlePacket {

	protected MiddleSyncPing(MiddlePacketInit init) {
		super(init);
	}

	protected int id;

	@Override
	protected void decode(ByteBuf serverdata) {
		id = serverdata.readInt();
	}

}
