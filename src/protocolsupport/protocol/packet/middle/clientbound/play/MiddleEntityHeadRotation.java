package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityHeadRotation extends MiddleEntity {

	protected byte headRot;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		headRot = serverdata.readByte();
	}
	
	@Override
	public void handle() {
		cache.updateWatchedHeadRotation(entityId, headRot);
	}

}
