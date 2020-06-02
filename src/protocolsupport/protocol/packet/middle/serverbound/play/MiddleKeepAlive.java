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
	protected void writeToServer() {
		if (keepAliveId != -1) {
			ServerBoundPacketData keepalive = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_KEEP_ALIVE);
			keepalive.writeLong(keepAliveId);
			codec.read(keepalive);
		}
	}

}
