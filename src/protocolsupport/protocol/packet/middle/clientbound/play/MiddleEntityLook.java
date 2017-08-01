package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityLook extends MiddleEntity {

	protected int yaw;
	protected int pitch;
	protected boolean onGround;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();
	}
	
	@Override
	public void handle() {
		cache.updateWatchedRotation(entityId, (byte) yaw, (byte) pitch);
		cache.updateWatchedHeadRotation(entityId, (byte) yaw);
		cache.updateWatchedOnGround(entityId, onGround);
	}

}
