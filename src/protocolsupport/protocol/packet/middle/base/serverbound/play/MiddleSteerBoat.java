package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleSteerBoat extends ServerBoundMiddlePacket {

	protected MiddleSteerBoat(IMiddlePacketInit init) {
		super(init);
	}

	protected boolean rightPaddleTurning;
	protected boolean leftPaddleTurning;

	@Override
	protected void write() {
		io.writeServerbound(create(rightPaddleTurning, leftPaddleTurning));
	}

	public static ServerBoundPacketData create(boolean rightPaddleTurning, boolean leftPaddleTurning) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_STEER_BOAT);
		creator.writeBoolean(rightPaddleTurning);
		creator.writeBoolean(leftPaddleTurning);
		return creator;
	}

}
