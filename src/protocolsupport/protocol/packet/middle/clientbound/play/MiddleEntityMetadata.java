package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.typeremapper.watchedentity.DataWatcherRemapper;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;

public abstract class MiddleEntityMetadata extends MiddleEntity {

	public MiddleEntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected DataWatcherRemapper metadata = new DataWatcherRemapper();

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		metadata.init(serverdata, connection.getVersion(), cache.getAttributesCache().getLocale(), entity);
	}

	@Override
	public boolean postFromServerRead() {
		return entity != null;
	}

}
