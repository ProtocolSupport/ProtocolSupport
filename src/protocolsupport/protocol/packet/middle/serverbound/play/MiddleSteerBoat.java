package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSteerBoat extends ServerBoundMiddlePacket {

	public MiddleSteerBoat(ConnectionImpl connection) {
		super(connection);
	}

	protected boolean rightPaddleTurning;
	protected boolean leftPaddleTurning;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(rightPaddleTurning, leftPaddleTurning));
	}

	public static ServerBoundPacketData create(boolean rightPaddleTurning, boolean leftPaddleTurning) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_STEER_BOAT);
		creator.writeBoolean(rightPaddleTurning);
		creator.writeBoolean(leftPaddleTurning);
		return creator;
	}

}
