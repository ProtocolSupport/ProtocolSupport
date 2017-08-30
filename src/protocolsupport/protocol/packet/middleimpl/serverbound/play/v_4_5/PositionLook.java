package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
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
	public void readFromClientData(ByteBuf clientdata) {
		x = clientdata.readDouble();
		y = clientdata.readDouble();
		yhead = clientdata.readDouble();
		z = clientdata.readDouble();
		yaw = clientdata.readFloat();
		pitch = clientdata.readFloat();
		onGround = clientdata.readBoolean();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if ((y == -999.0D) && (yhead == -999.0D)) {
			ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_LOOK);
			creator.writeFloat(yaw);
			creator.writeFloat(pitch);
			creator.writeBoolean(onGround);
			return RecyclableSingletonList.create(creator);
		} else {
			if (!cache.isTeleportConfirmNeeded()) {
				ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_POSITION_LOOK);
				creator.writeDouble(x);
				creator.writeDouble(y);
				creator.writeDouble(z);
				creator.writeFloat(yaw);
				creator.writeFloat(pitch);
				creator.writeBoolean(onGround);
				return RecyclableSingletonList.create(creator);
			} else {
				int teleportId = cache.tryTeleportConfirm(x, y, z);
				if (teleportId == -1) {
					return RecyclableEmptyList.get();
				} else {
					RecyclableCollection<ServerBoundPacketData> collection = RecyclableArrayList.create();
					collection.add(MiddleTeleportAccept.create(teleportId));
					collection.add(createMoveLookPacket());
					return collection;
				}
			}
		}
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
