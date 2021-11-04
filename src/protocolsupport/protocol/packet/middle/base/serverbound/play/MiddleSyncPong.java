package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleSyncPong extends ServerBoundMiddlePacket {

	protected MiddleSyncPong(IMiddlePacketInit init) {
		super(init);
	}

	protected int id;

	@Override
	protected void write() {
		io.writeServerbound(create(id));
	}

	public static ServerBoundPacketData create(int id) {
		ServerBoundPacketData syncpongPacket = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_SYNC_PONG);
		syncpongPacket.writeInt(id);
		return syncpongPacket;
	}

}
