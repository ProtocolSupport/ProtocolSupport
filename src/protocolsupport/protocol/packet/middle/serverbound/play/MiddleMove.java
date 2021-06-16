package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleMove extends ServerBoundMiddlePacket {

	protected MiddleMove(MiddlePacketInit init) {
		super(init);
	}

	protected double x;
	protected double y;
	protected double z;
	protected boolean onGround;

	@Override
	protected void write() {
		ServerBoundPacketData move = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_POSITION);
		move.writeDouble(x);
		move.writeDouble(y);
		move.writeDouble(z);
		move.writeBoolean(onGround);
		codec.writeServerbound(move);
	}

}
