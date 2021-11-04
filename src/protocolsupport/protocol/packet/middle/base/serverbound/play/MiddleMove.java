package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleMove extends ServerBoundMiddlePacket {

	protected MiddleMove(IMiddlePacketInit init) {
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
		io.writeServerbound(move);
	}

}
