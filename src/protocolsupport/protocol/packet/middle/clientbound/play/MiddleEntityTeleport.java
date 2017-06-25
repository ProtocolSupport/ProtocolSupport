package protocolsupport.protocol.packet.middle.clientbound.play;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityTeleport extends MiddleEntity {

	protected double x;
	protected double y;
	protected double z;
	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();
	}
	
	@Override
	public void handle() {
		cache.updateWatchedPosition(entityId, new Vector(x, y, z));
		cache.updateWatchedRotation(entityId, yaw, pitch);
		cache.updateWatchedHeadRotation(entityId, yaw);
		cache.updateWatchedOnGround(entityId, onGround);
	}

}
