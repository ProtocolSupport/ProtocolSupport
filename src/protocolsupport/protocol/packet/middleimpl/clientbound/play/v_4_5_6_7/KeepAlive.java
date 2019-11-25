package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKeepAlive;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class KeepAlive extends MiddleKeepAlive {

	public KeepAlive(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData keepalive = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_KEEP_ALIVE);
		keepalive.writeInt(keepAliveId);
		codec.write(keepalive);
	}

}
