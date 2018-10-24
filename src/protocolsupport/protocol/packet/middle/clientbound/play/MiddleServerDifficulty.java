package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.utils.types.Difficulty;

public abstract class MiddleServerDifficulty extends ClientBoundMiddlePacket {

	public MiddleServerDifficulty(ConnectionImpl connection) {
		super(connection);
	}

	protected Difficulty difficulty;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		difficulty = Difficulty.getById(serverdata.readByte());
	}

}
