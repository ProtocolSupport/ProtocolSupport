package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.AttributesCache;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.storage.netcache.window.WindowCache;
import protocolsupport.protocol.typeremapper.window.AbstractWindowsRemapper;
import protocolsupport.protocol.typeremapper.window.WindowsRemappersRegistry;
import protocolsupport.protocol.types.Environment;
import protocolsupport.protocol.types.GameMode;
import protocolsupport.protocol.types.WindowType;

public abstract class MiddleChangeDimension extends ClientBoundMiddlePacket {

	protected final AttributesCache clientCache = cache.getAttributesCache();
	protected final NetworkEntityCache entityCache = cache.getEntityCache();
	protected final WindowCache windowCache = cache.getWindowCache();

	protected final AbstractWindowsRemapper windowRemapper = WindowsRemappersRegistry.get(version);

	public MiddleChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	protected Environment dimension;
	protected long hashedSeed;
	protected GameMode gamemode;
	protected String leveltype;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		dimension = Environment.getById(serverdata.readInt());
		hashedSeed = serverdata.readLong();
		gamemode = GameMode.getById(serverdata.readByte());
		leveltype = StringSerializer.readVarIntUTF8String(serverdata);
	}

	@Override
	public void handleReadData() {
		clientCache.setCurrentDimension(dimension);
		entityCache.clearEntities();
		windowCache.setPlayerWindow(windowRemapper.get(WindowType.PLAYER, 0));
	}

}
