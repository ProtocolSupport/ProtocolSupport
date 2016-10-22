package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSteerBoat extends ServerBoundMiddlePacket {

	protected boolean rightPaddleTurning;
	protected boolean leftPaddleTurning;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_STEER_BOAT);
		creator.writeBoolean(rightPaddleTurning);
		creator.writeBoolean(leftPaddleTurning);
		return RecyclableSingletonList.create(creator);
	}

}
