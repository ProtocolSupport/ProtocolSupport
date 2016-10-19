package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePosition extends ServerBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double z;
	protected boolean onGround;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() throws Exception {
		if (!cache.isTeleportConfirmNeeded()) {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_POSITION);
			creator.writeDouble(x);
			creator.writeDouble(y);
			creator.writeDouble(z);
			creator.writeBoolean(onGround);
			return RecyclableSingletonList.create(creator);
		} else {
			return RecyclableEmptyList.get();
		}
	}

}
