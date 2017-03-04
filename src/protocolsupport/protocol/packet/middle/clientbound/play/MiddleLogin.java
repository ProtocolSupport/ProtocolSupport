package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedPlayer;

public abstract class MiddleLogin extends ClientBoundMiddlePacket {

	protected int playerEntityId;
	protected int gamemode;
	protected int dimension;
	protected int difficulty;
	protected int maxplayers;
	protected String leveltype;
	protected boolean reducedDebugInfo;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		playerEntityId = serverdata.readInt();
		gamemode = serverdata.readByte();
		dimension = IdRemapper.fixDimensionId(serverdata.readInt());
		difficulty = serverdata.readByte();
		serverdata.readByte();
		maxplayers = TabAPI.getMaxTabSize();
		leveltype = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(), 16);
		reducedDebugInfo = serverdata.readBoolean();
	}

	@Override
	public void handle() {
		cache.addWatchedSelfPlayer(new WatchedPlayer(playerEntityId));
		cache.setDimensionId(dimension);
	}

}
