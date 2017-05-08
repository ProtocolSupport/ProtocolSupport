package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedEntity;
import protocolsupport.protocol.utils.types.Difficulty;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.protocol.utils.types.GameMode;

public abstract class MiddleLogin extends ClientBoundMiddlePacket {

	protected int playerEntityId;
	protected GameMode gamemode;
	protected boolean hardcore;
	protected Environment dimension;
	protected Difficulty difficulty;
	protected int maxplayers;
	protected String leveltype;
	protected boolean reducedDebugInfo;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		playerEntityId = serverdata.readInt();
		int gmdata = serverdata.readByte();
		gamemode = GameMode.getById(gmdata & 0b11);
		hardcore = (gmdata & 0b100) == 1 ? true : false;
		dimension = Environment.getById(serverdata.readInt());
		difficulty = Difficulty.getById(serverdata.readByte());
		serverdata.readByte();
		maxplayers = TabAPI.getMaxTabSize();
		leveltype = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(ProtocolType.PC), 16);
		reducedDebugInfo = serverdata.readBoolean();
	}

	@Override
	public void handle() {
		cache.addWatchedSelfPlayer(WatchedEntity.createPlayer(playerEntityId));
		cache.setDimensionId(dimension);
	}

}
