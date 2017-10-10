package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.types.GameMode;

public abstract class MiddleGameStateChange extends ClientBoundMiddlePacket {

	protected int type;
	protected float value;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		type = serverdata.readByte();
		value = serverdata.readFloat();
	}
	
	@Override
	public boolean postFromServerRead() {
		if(type == 3) {
			cache.setGameMode(GameMode.getById((int) value));
		}
		return true;
	}

}
