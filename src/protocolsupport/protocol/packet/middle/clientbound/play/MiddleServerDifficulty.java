package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.types.Difficulty;

public abstract class MiddleServerDifficulty extends ClientBoundMiddlePacket {

	public MiddleServerDifficulty(ConnectionImpl connection) {
		super(connection);
	}

	protected Difficulty difficulty;
	protected boolean locked;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		difficulty = MiscSerializer.readByteEnum(serverdata, Difficulty.CONSTANT_LOOKUP);
		locked = serverdata.readBoolean();
	}

}
