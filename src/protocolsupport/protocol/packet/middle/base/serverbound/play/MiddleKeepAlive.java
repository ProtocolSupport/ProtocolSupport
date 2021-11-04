package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleKeepAlive extends ServerBoundMiddlePacket {

	protected MiddleKeepAlive(IMiddlePacketInit init) {
		super(init);
	}

	protected long keepAliveId;

	@Override
	protected void write() {
		if (keepAliveId != -1) {
			ServerBoundPacketData keepalive = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_KEEP_ALIVE);
			keepalive.writeLong(keepAliveId);
			io.writeServerbound(keepalive);
		}
	}

}
