package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.Difficulty;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.protocol.utils.types.NetworkEntity;

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
		gamemode = GameMode.getById(gmdata & 0xFFFFFFF7);
		hardcore = (gmdata & 0x8) == 0x8 ? true : false;
		dimension = Environment.getById(serverdata.readInt());
		difficulty = Difficulty.getById(serverdata.readByte());
		serverdata.readByte();
		maxplayers = TabAPI.getMaxTabSize();
		leveltype = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
		reducedDebugInfo = serverdata.readBoolean();
	}

	@Override
	public boolean postFromServerRead() {
		cache.addWatchedSelfPlayer(NetworkEntity.createPlayer(playerEntityId));
		cache.setDimensionId(dimension);
		return true;
	}

}
