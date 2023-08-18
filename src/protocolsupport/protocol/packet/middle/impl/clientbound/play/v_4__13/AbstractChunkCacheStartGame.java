package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15.AbstractLegacyStartGame;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public abstract class AbstractChunkCacheStartGame extends AbstractLegacyStartGame {

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();

	protected AbstractChunkCacheStartGame(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		super.handle();
		clientCache.setDimensionSkyLight(LegacyDimension.hasSkyLight(dimensionId));
		chunkCache.clear();
	}

}
