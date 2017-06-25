package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityRelMove extends MiddleEntity {

	protected int relX;
	protected int relY;
	protected int relZ;
	protected boolean onGround;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		relX = serverdata.readShort();
		relY = serverdata.readShort();
		relZ = serverdata.readShort();
		onGround = serverdata.readBoolean();
	}
	
	@Override
	public void handle() {
		cache.updateWatchedRelPosition(entityId, relX, relY, relZ);
		cache.updateWatchedOnGround(entityId, onGround);
	}
	
}
