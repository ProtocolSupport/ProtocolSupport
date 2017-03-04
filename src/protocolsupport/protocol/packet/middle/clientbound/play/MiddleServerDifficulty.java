package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleServerDifficulty extends ClientBoundMiddlePacket {

	protected int difficulty;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		difficulty = serverdata.readByte();
	}

}
