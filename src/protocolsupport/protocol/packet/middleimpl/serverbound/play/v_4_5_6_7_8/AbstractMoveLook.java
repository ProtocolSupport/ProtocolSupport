package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMoveLook;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTeleportAccept;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.storage.netcache.MovementCache;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class AbstractMoveLook extends ServerBoundMiddlePacket {

	protected final MovementCache movementCache = cache.getMovementCache();

	public AbstractMoveLook(ConnectionImpl connection) {
		super(connection);
	}

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	public RecyclableCollection<? extends IPacketData> toNative() {
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
