package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractLegacyStartGame;
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
