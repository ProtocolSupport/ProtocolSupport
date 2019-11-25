package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleMove extends ServerBoundMiddlePacket {

	public MiddleMove(ConnectionImpl connection) {
		super(connection);
	}

	protected double x;
	protected double y;
	protected double z;
	protected boolean onGround;

	@Override
	public void writeToServer() {
		ServerBoundPacketData move = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_POSITION);
		move.writeDouble(x);
		move.writeDouble(y);
		move.writeDouble(z);
		move.writeBoolean(onGround);
		codec.read(move);
	}

}
