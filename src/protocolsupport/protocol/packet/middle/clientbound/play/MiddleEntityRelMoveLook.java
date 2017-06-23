package protocolsupport.protocol.packet.middle.clientbound.play;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityRelMoveLook extends MiddleEntity {

	protected int relX;
	protected int relY;
	protected int relZ;
	protected int yaw;
	protected int pitch;
	protected boolean onGround;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		relX = serverdata.readShort();
		relY = serverdata.readShort();
		relZ = serverdata.readShort();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();
	}

	@Override
	public void handle() {
		cache.updateWatchedPosition(entityId, new Vector(relX, relY, relZ));
		cache.updateWatchedRotation(entityId, (byte) yaw, (byte) pitch);
	}
	
}
