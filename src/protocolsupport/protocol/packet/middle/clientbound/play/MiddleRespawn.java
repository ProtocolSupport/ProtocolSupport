package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;

public abstract class MiddleRespawn extends ClientBoundMiddlePacket {

	protected int dimension;
	protected int difficulty;
	protected int gamemode;
	protected String leveltype;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		dimension = IdRemapper.fixDimensionId(serverdata.readInt());
		difficulty = serverdata.readByte();
		gamemode = serverdata.readByte();
		leveltype = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 16);
	}

	@Override
	public void handle() {
		cache.clearWatchedEntities();
		cache.setDimensionId(dimension);
	}

}
