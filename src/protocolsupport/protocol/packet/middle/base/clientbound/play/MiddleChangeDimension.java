package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.PositionCodec;
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
import protocolsupport.protocol.types.WorldPosition;

public abstract class MiddleChangeDimension extends ClientBoundMiddlePacket {

	protected MiddleChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	protected static final int KEEP_DATA_FLAGS_BIT_METADATA = 0;
	protected static final int KEEP_DATA_FLAGS_BIT_ATTRIBUTES = 1;

	protected final ClientCache clientCache = cache.getClientCache();
	protected final NetworkEntityCache entityCache = cache.getEntityCache();
	protected final WindowCache windowCache = cache.getWindowCache();

	protected final WindowsRemapper windowsRemapper = WindowsRemappersRegistry.get(version);


	protected String worldType;
	protected String worldName;
	protected long hashedSeed;
	protected GameMode gamemodeCurrent;
	protected GameMode gamemodePrevious;
	protected boolean worldDebug;
	protected boolean worldFlat;
	protected byte keepDataFlags; //TODO: handle in <= 1.15.2 impls
	protected WorldPosition deathPosition;
	protected int portalCooldown;

	@Override
	protected void decode(ByteBuf serverdata) {
		worldType = StringCodec.readVarIntUTF8String(serverdata);
		worldName = StringCodec.readVarIntUTF8String(serverdata);
		hashedSeed = serverdata.readLong();
		gamemodeCurrent = GameMode.getById(serverdata.readByte());
		gamemodePrevious = GameMode.getById(serverdata.readByte());
		worldDebug = serverdata.readBoolean();
		worldFlat = serverdata.readBoolean();
		keepDataFlags = serverdata.readByte();
		deathPosition = OptionalCodec.readOptional(serverdata, PositionCodec::readWorldPosition);
		portalCooldown = VarNumberCodec.readVarInt(serverdata);
	}

	@Override
	protected void handle() {
		clientCache.setCurrentWorld(worldType);
		entityCache.clearEntities();
		windowCache.setPlayerWindow(windowsRemapper.get(WindowType.PLAYER, 0));
	}

}
