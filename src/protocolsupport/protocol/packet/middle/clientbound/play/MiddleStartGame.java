package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.typeremapper.window.AbstractWindowsRemapper;
import protocolsupport.protocol.typeremapper.window.WindowsRemappersRegistry;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleStartGame extends ClientBoundMiddlePacket {

	protected final ClientCache clientCache = cache.getClientCache();
	protected final NetworkEntityCache entityCache = cache.getEntityCache();
	protected final WindowCache windowCache = cache.getWindowCache();

	protected final AbstractWindowsRemapper windowRemapper = WindowsRemappersRegistry.get(version);

	public MiddleStartGame(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity player;
	protected GameMode gamemodeCurrent;
	protected boolean hardcore;
	protected GameMode gamemodePrevious;
	protected String[] worlds;
	protected NBTCompound dimensions;
	protected String dimension;
	protected String world;
	protected long hashedSeed;
	protected int maxplayers;
	protected int renderDistance;
	protected boolean reducedDebugInfo;
	protected boolean respawnScreenEnabled;
	protected boolean worldDebug;
	protected boolean worldFlat;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		player = NetworkEntity.createPlayer(serverdata.readInt());
		int gmdata = serverdata.readByte();
		gamemodeCurrent = GameMode.getById(gmdata & 0xFFFFFFF7);
		hardcore = (gmdata & 0x8) == 0x8;
		gamemodePrevious = GameMode.getById(serverdata.readByte());
		worlds = ArraySerializer.readVarIntVarIntUTF8StringArray(serverdata);
		dimensions = ItemStackSerializer.readDirectTag(serverdata);
		dimension = StringSerializer.readVarIntUTF8String(serverdata);
		world = StringSerializer.readVarIntUTF8String(serverdata);
		hashedSeed = serverdata.readLong();
		serverdata.readByte();
		maxplayers = TabAPI.getMaxTabSize();
		renderDistance = VarNumberSerializer.readVarInt(serverdata);
		reducedDebugInfo = serverdata.readBoolean();
		respawnScreenEnabled = serverdata.readBoolean();
		worldDebug = serverdata.readBoolean();
		worldFlat = serverdata.readBoolean();
	}

	@Override
	protected void handleReadData() {
		clientCache.setDimensions(dimensions);
		clientCache.setCurrentDimension(dimension);
		clientCache.setRespawnScreenEnabled(respawnScreenEnabled);
		windowCache.setPlayerWindow(windowRemapper.get(WindowType.PLAYER, 0));
		entityCache.clearEntities();
		entityCache.setSelf(player);
	}

}
