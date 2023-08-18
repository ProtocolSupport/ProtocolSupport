package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13;

import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15.AbstractLegacyChangeDimension;
import protocolsupport.protocol.storage.netcache.chunk.LimitedHeightChunkCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public abstract class AbstractChunkCacheChangeDimension extends AbstractLegacyChangeDimension {

	protected final LimitedHeightChunkCache chunkCache = cache.getChunkCache();

	protected AbstractChunkCacheChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		super.handle();
		clientCache.setDimensionSkyLight(LegacyDimension.hasSkyLight(dimensionId));
		if (!sameWorld) {
			chunkCache.clear();
		}
	}

}
