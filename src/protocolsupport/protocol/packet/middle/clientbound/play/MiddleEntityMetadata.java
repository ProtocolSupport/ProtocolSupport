package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.typeremapper.watchedentity.DataWatcherDataRemapper;
import protocolsupport.protocol.utils.types.NetworkEntity;

public abstract class MiddleEntityMetadata extends MiddleEntity {

	protected NetworkEntity entity;
	protected DataWatcherDataRemapper metadata = new DataWatcherDataRemapper();

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		entity = cache.getWatchedEntity(entityId);
		metadata.init(serverdata, connection.getVersion(), cache.getLocale(), entity);
	}

	@Override
	public boolean postFromServerRead() {
		return entity != null;
	}

}
