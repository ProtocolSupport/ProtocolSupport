package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleSteerBoat extends ServerBoundMiddlePacket {

	public MiddleSteerBoat(ConnectionImpl connection) {
		super(connection);
	}

	protected boolean rightPaddleTurning;
	protected boolean leftPaddleTurning;

	@Override
	protected void writeToServer() {
		codec.read(create(rightPaddleTurning, leftPaddleTurning));
	}

	public static ServerBoundPacketData create(boolean rightPaddleTurning, boolean leftPaddleTurning) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_STEER_BOAT);
		creator.writeBoolean(rightPaddleTurning);
		creator.writeBoolean(leftPaddleTurning);
		return creator;
	}

}
