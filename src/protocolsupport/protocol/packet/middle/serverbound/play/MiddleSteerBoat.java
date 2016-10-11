package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleSteerBoat extends ServerBoundMiddlePacket {

	protected boolean rightPaddleTurning;
	protected boolean leftPaddleTurning;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_RESOURCE_PACK_STATUS);
		creator.writeBoolean(rightPaddleTurning);
		creator.writeBoolean(leftPaddleTurning);
		return RecyclableSingletonList.create(creator);
	}

}
