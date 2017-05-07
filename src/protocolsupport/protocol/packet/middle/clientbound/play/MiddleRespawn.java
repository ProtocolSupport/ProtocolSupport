package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.types.Difficulty;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.protocol.utils.types.GameMode;

public abstract class MiddleRespawn extends ClientBoundMiddlePacket {

	protected Environment dimension;
	protected Difficulty difficulty;
	protected GameMode gamemode;
	protected String leveltype;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		dimension = Environment.getById(serverdata.readInt());
		difficulty = Difficulty.getById(serverdata.readByte());
		gamemode = GameMode.getById(serverdata.readByte());
		leveltype = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(ProtocolType.PC), 16);
	}

	@Override
	public void handle() {
		cache.clearWatchedEntities();
		cache.setDimensionId(dimension);
	}

}
