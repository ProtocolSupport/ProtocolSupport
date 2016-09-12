package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class PositionLook extends ServerBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double yhead;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		 x = serializer.readDouble();
		 y = serializer.readDouble();
		 yhead = serializer.readDouble();
		 z = serializer.readDouble();
		 yaw = serializer.readFloat();
		 pitch = serializer.readFloat();
		 onGround = serializer.readBoolean();
	}

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		if ((y == -999.0D) && (yhead == -999.0D)) {
			PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_LOOK);
			creator.writeFloat(yaw);
			creator.writeFloat(pitch);
			creator.writeBoolean(onGround);
			return RecyclableSingletonList.create(creator);
		} else {
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

}
