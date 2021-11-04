package protocolsupport.protocol.packet.middle.base.serverbound.status;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddlePing extends ServerBoundMiddlePacket {

	protected MiddlePing(IMiddlePacketInit init) {
		super(init);
	}

	protected long pingId;

	@Override
	protected void write() {
		ServerBoundPacketData ping = ServerBoundPacketData.create(ServerBoundPacketType.STATUS_PING);
		ping.writeLong(pingId);
		io.writeServerbound(ping);
	}

}
