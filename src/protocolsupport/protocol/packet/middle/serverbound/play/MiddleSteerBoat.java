package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleSteerBoat extends ServerBoundMiddlePacket {

	protected MiddleSteerBoat(MiddlePacketInit init) {
		super(init);
	}

	protected boolean rightPaddleTurning;
	protected boolean leftPaddleTurning;

	@Override
	protected void write() {
		codec.writeServerbound(create(rightPaddleTurning, leftPaddleTurning));
	}

	public static ServerBoundPacketData create(boolean rightPaddleTurning, boolean leftPaddleTurning) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_PLAY_STEER_BOAT);
		creator.writeBoolean(rightPaddleTurning);
		creator.writeBoolean(leftPaddleTurning);
		return creator;
	}

}
