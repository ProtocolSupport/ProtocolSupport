package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleSyncPong extends ServerBoundMiddlePacket {

	protected MiddleSyncPong(MiddlePacketInit init) {
		super(init);
	}

	protected int id;

	@Override
	protected void write() {
		codec.writeServerbound(create(id));
	}

	public static ServerBoundPacketData create(int id) {
		ServerBoundPacketData syncpongPacket = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_SYNC_PONG);
		syncpongPacket.writeInt(id);
		return syncpongPacket;
	}

}
