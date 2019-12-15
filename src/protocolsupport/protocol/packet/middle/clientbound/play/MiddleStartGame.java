package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.window.WindowsRemappersRegistry;
import protocolsupport.protocol.types.Environment;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleStartGame extends ClientBoundMiddlePacket {

	public MiddleStartGame(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity player;
	protected GameMode gamemode;
	protected boolean hardcore;
	protected Environment dimension;
	protected long hashedSeed;
	protected int maxplayers;
	protected String leveltype;
	protected int renderDistance;
	protected boolean reducedDebugInfo;
	protected boolean enableRespawnScreen;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		player = NetworkEntity.createPlayer(serverdata.readInt());
		int gmdata = serverdata.readByte();
		gamemode = GameMode.getById(gmdata & 0xFFFFFFF7);
		hardcore = (gmdata & 0x8) == 0x8;
		dimension = Environment.getById(serverdata.readInt());
		hashedSeed = serverdata.readLong();
		serverdata.readByte();
		maxplayers = TabAPI.getMaxTabSize();
		leveltype = StringSerializer.readVarIntUTF8String(serverdata);
		renderDistance = VarNumberSerializer.readVarInt(serverdata);
		reducedDebugInfo = serverdata.readBoolean();
		enableRespawnScreen = serverdata.readBoolean();
	}

	@Override
	public boolean postFromServerRead() {
		cache.getWatchedEntityCache().addWatchedSelfPlayer(player);
		cache.getAttributesCache().setCurrentDimension(dimension);
		cache.getWindowCache().setPlayerWindow(WindowsRemappersRegistry.get(version).get(WindowType.PLAYER, 0));
		return true;
	}

}
