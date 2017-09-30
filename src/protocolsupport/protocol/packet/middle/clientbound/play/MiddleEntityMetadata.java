package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class MiddleEntityMetadata extends MiddleEntity {

	protected NetworkEntity entity;
	protected ArrayMap<DataWatcherObject<?>> metadata;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		entity = cache.getWatchedEntity(entityId);
		metadata = DataWatcherDeserializer.decodeData(serverdata, ProtocolVersionsHelper.LATEST_PC, cache.getLocale());
	}

	@Override
	public boolean postFromServerRead() {
		return entity != null;
	}

}
