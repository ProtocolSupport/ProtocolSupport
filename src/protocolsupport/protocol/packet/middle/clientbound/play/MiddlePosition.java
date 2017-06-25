package protocolsupport.protocol.packet.middle.clientbound.play;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.types.NetworkEntity;

public abstract class MiddlePosition extends ClientBoundMiddlePacket {

	protected int entityId;
	protected double xOrig;
	protected double yOrig;
	protected double zOrig;
	protected float yawOrig;
	protected float pitchOrig;
	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;
	protected int flags;
	protected int teleportConfirmId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		NetworkEntity entity = cache.getWatchedSelf();
		entityId = entity.getId();
		xOrig = x = serverdata.readDouble();
		yOrig = y = serverdata.readDouble();
		zOrig = z = serverdata.readDouble();
		yawOrig = yaw = serverdata.readFloat();
		pitchOrig = pitch = serverdata.readFloat();
		flags = serverdata.readByte();
		if (flags != 0) {
			//Location location = connection.getPlayer().getLocation();
			Vector pos = cache.getWatchedSelf().getPosition();
			if ((flags & 0x01) != 0) {
				x += pos.getX();
			}
			if ((flags & 0x02) != 0) {
				y += pos.getY();
			}
			if ((flags & 0x04) != 0) {
				z += pos.getZ();
			}
			if ((flags & 0x08) != 0) {
				yaw += entity.getYaw();
			}
			if ((flags & 0x10) != 0) {
				pitch += entity.getPitch();
			}
		}
		teleportConfirmId = VarNumberSerializer.readVarInt(serverdata);
	}

	@Override
	public void handle() {
		cache.updateWatchedPosition(entityId, new Vector(x, y , z));
		cache.updateWatchedRotation(entityId, (byte) yaw, (byte) pitch);
		cache.updateWatchedHeadRotation(entityId, (byte) yaw);
		
		if (teleportConfirmId != 0) {
			cache.setTeleportLocation(x, y, z, teleportConfirmId);
		}
	}

}
