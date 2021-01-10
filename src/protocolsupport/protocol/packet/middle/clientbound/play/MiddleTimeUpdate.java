package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleTimeUpdate extends ClientBoundMiddlePacket {

	public MiddleTimeUpdate(MiddlePacketInit init) {
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
