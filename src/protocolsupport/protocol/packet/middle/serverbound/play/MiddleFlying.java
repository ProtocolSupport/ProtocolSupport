package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleFlying extends ServerBoundMiddlePacket {

	public MiddleFlying(ConnectionImpl connection) {
		super(connection);
	}

	protected boolean onGround;

	@Override
	public void writeToServer() {
		ServerBoundPacketData flying = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_PLAYER);
		flying.writeBoolean(onGround);
		codec.read(flying);
	}

}
