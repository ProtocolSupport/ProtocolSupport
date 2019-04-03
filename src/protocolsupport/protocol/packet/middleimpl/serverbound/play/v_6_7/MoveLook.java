package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMoveLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class MoveLook extends ServerBoundMiddlePacket {

	public MoveLook(ConnectionImpl connection) {
		super(connection);
	}

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		x = clientdata.readDouble();
		y = clientdata.readDouble();
		clientdata.readDouble();
		z = clientdata.readDouble();
		yaw = clientdata.readFloat();
		pitch = clientdata.readFloat();
		onGround = clientdata.readBoolean();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		int teleportId = cache.getMovementCache().tryTeleportConfirm(x, y, z);
		if (teleportId == -1) {
			return RecyclableSingletonList.create(MiddleMoveLook.create(x, y, z, yaw, pitch, onGround));
		} else {
			RecyclableArrayList<ServerBoundPacketData> packets = RecyclableArrayList.create();
			packets.add(MiddleTeleportAccept.create(teleportId));
			packets.add(MiddleMoveLook.create(x, y, z, yaw, pitch, onGround));
			return packets;
		}
	}

}
