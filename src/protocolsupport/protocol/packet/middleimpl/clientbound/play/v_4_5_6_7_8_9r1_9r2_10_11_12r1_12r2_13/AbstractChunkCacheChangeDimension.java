package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractLegacyChangeDimension;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public abstract class AbstractChunkCacheChangeDimension extends AbstractLegacyChangeDimension {

	protected final ChunkCache chunkCache = cache.getChunkCache();

	public AbstractChunkCacheChangeDimension(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handleReadData() {
		super.handleReadData();
		clientCache.setDimensionSkyLight(LegacyDimension.hasSkyLight(dimensionId));
		if (!sameWorld) {
			chunkCache.clear();
		}
	}

}
