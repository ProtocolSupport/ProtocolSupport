package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleTimeUpdate extends ClientBoundMiddlePacket {

	protected MiddleTimeUpdate(IMiddlePacketInit init) {
		super(init);
	}

	protected long worldAge;
	protected long timeOfDay;

	@Override
	protected void decode(ByteBuf serverdata) {
		worldAge = serverdata.readLong();
		timeOfDay = serverdata.readLong();
	}

}
