package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleKeepAlive extends ServerBoundMiddlePacket {

	public MiddleKeepAlive(ConnectionImpl connection) {
		super(connection);
	}

	protected long keepAliveId;

	@Override
	public void writeToServer() {
		if (keepAliveId != -1) {
			codec.read(create(keepAliveId));
		}
	}

	public static ServerBoundPacketData create(long keepAliveId) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_KEEP_ALIVE);
		creator.writeLong(keepAliveId);
		return creator;
	}

}
