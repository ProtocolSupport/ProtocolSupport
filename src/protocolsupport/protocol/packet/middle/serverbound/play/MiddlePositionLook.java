package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePositionLook extends ServerBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		if (!sharedstorage.isTeleportConfirmNeeded()) {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_POSITION_LOOK);
			creator.writeDouble(x);
			creator.writeDouble(y);
			creator.writeDouble(z);
			creator.writeFloat(yaw);
			creator.writeFloat(pitch);
			creator.writeBoolean(onGround);
			return RecyclableSingletonList.create(creator);
		} else {
			int teleportId = sharedstorage.tryTeleportConfirm(x, y, z);
			if (teleportId == -1) {
				return RecyclableEmptyList.get();
			} else {
				PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_TELEPORT_ACCEPT);
				creator.writeVarInt(teleportId);
				return RecyclableSingletonList.create(creator);
			}
		}
	}

}
