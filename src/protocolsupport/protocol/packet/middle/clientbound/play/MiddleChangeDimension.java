package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.typeremapper.window.WindowsRemapper;
import protocolsupport.protocol.typeremapper.window.WindowsRemappersRegistry;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class MiddleChangeDimension extends ClientBoundMiddlePacket {

	protected MiddleChangeDimension(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();
	protected final NetworkEntityCache entityCache = cache.getEntityCache();
	protected final WindowCache windowCache = cache.getWindowCache();

	protected final WindowsRemapper windowsRemapper = WindowsRemappersRegistry.get(version);


	protected NBTCompound dimension;
	protected String world;
	protected long hashedSeed;
	protected GameMode gamemodeCurrent;
	protected GameMode gamemodePrevious;
	protected boolean worldDebug;
	protected boolean worldFlat;
	//TODO: handle in <= 1.15.2 impls
	protected boolean keepEntityMetadata;

	@Override
	protected void decode(ByteBuf serverdata) {
		dimension = ItemStackCodec.readDirectTag(serverdata);
		world = StringCodec.readVarIntUTF8String(serverdata);
		hashedSeed = serverdata.readLong();
		gamemodeCurrent = GameMode.getById(serverdata.readByte());
		gamemodePrevious = GameMode.getById(serverdata.readByte());
		worldDebug = serverdata.readBoolean();
		worldFlat = serverdata.readBoolean();
		keepEntityMetadata = serverdata.readBoolean();
	}

	@Override
	protected void handle() {
		clientCache.setCurrentWorld(world, dimension);
		entityCache.clearEntities();
		windowCache.setPlayerWindow(windowsRemapper.get(WindowType.PLAYER, 0));
	}

}
