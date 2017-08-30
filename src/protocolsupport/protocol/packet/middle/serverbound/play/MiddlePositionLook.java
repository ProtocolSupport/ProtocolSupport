package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePositionLook extends ServerBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (cache.isTeleportConfirmNeeded()) {
			int teleportId = cache.tryTeleportConfirm(x, y, z);
			if (teleportId != -1) {
				RecyclableCollection<ServerBoundPacketData> collection = RecyclableArrayList.create();
				collection.add(MiddleTeleportAccept.create(teleportId));
				collection.add(createMoveLookPacket());
				return collection;
			}
		}

		return RecyclableSingletonList.create(createMoveLookPacket());
	}

	private ServerBoundPacketData createMoveLookPacket() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_POSITION_LOOK);
		creator.writeDouble(x);
		creator.writeDouble(y);
		creator.writeDouble(z);
		creator.writeFloat(yaw);
		creator.writeFloat(pitch);
		creator.writeBoolean(onGround);
		return creator;
	}
}
