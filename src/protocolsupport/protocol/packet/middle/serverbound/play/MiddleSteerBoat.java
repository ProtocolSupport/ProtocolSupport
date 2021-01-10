package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleSteerBoat extends ServerBoundMiddlePacket {

	public MiddleSteerBoat(MiddlePacketInit init) {
		super(init);
	}

	protected boolean rightPaddleTurning;
	protected boolean leftPaddleTurning;

	@Override
	protected void write() {
		codec.writeServerbound(create(rightPaddleTurning, leftPaddleTurning));
	}

	public static ServerBoundPacketData create(boolean rightPaddleTurning, boolean leftPaddleTurning) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_STEER_BOAT);
		creator.writeBoolean(rightPaddleTurning);
		creator.writeBoolean(leftPaddleTurning);
		return creator;
	}

}
