package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.tab.TabAPI;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.typeremapper.window.WindowsRemapper;
import protocolsupport.protocol.typeremapper.window.WindowsRemappersRegistry;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleStartGame extends ClientBoundMiddlePacket {

	protected final ClientCache clientCache = cache.getClientCache();
	protected final NetworkEntityCache entityCache = cache.getEntityCache();
	protected final WindowCache windowCache = cache.getWindowCache();

	protected final WindowsRemapper windowsRemapper = WindowsRemappersRegistry.get(version);

	protected MiddleStartGame(IMiddlePacketInit init) {
		super(init);
	}

	protected NetworkEntity player;
	protected boolean hardcore;
	protected GameMode gamemodeCurrent;
	protected GameMode gamemodePrevious;
	protected String[] worlds;
	protected NBTCompound dimensions;
	protected NBTCompound dimension;
	protected String world;
	protected long hashedSeed;
	protected int maxplayers;
	protected int renderDistance;
	protected int simulationDistance;
	protected boolean reducedDebugInfo;
	protected boolean respawnScreenEnabled;
	protected boolean worldDebug;
	protected boolean worldFlat;

	@Override
	protected void decode(ByteBuf serverdata) {
		player = NetworkEntity.createPlayer(serverdata.readInt());
		hardcore = serverdata.readBoolean();
		gamemodeCurrent = GameMode.getById(serverdata.readByte());
		gamemodePrevious = GameMode.getById(serverdata.readByte());
		worlds = ArrayCodec.readVarIntVarIntUTF8StringArray(serverdata);
		dimensions = ItemStackCodec.readDirectTag(serverdata);
		dimension = ItemStackCodec.readDirectTag(serverdata);
		world = StringCodec.readVarIntUTF8String(serverdata);
		hashedSeed = serverdata.readLong();
		maxplayers = VarNumberCodec.readVarInt(serverdata);
		int forcedMaxPlayers = TabAPI.getMaxTabSize();
		if (forcedMaxPlayers >= 0) {
			maxplayers = forcedMaxPlayers;
		}
		renderDistance = VarNumberCodec.readVarInt(serverdata);
		simulationDistance = VarNumberCodec.readVarInt(serverdata);
		reducedDebugInfo = serverdata.readBoolean();
		respawnScreenEnabled = serverdata.readBoolean();
		worldDebug = serverdata.readBoolean();
		worldFlat = serverdata.readBoolean();
	}

	@Override
	protected void handle() {
		clientCache.setDimensionCodec(dimensions);
		clientCache.setCurrentWorld(world, dimension);
		clientCache.setRespawnScreenEnabled(respawnScreenEnabled);
		windowCache.setPlayerWindow(windowsRemapper.get(WindowType.PLAYER, 0));
		entityCache.clearEntities();
		entityCache.setSelf(player);
	}

}
