package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePositionLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
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
			return RecyclableSingletonList.create(MiddleLook.create(yaw, pitch, onGround));
		} else {
			int teleportId = cache.tryTeleportConfirm(x, y, z);
			if (teleportId == -1) {
				return RecyclableSingletonList.create(MiddlePositionLook.create(x, y, z, yaw, pitch, onGround));
			} else {
				RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
				packets.add(MiddleTeleportAccept.create(teleportId));
				packets.add(MiddlePositionLook.create(x, y, z, yaw, pitch, onGround));
				return packets;
			}
		}
	}

}
