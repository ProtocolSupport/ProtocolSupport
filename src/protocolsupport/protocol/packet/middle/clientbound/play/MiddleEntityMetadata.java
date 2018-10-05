package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.typeremapper.entity.EntityRemapper;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;

public abstract class MiddleEntityMetadata extends MiddleEntity {

	protected EntityRemapper entityRemapper = new EntityRemapper(connection.getVersion());

	public MiddleEntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		entityRemapper.readEntityWithMetadataAndRemap(cache.getAttributesCache().getLocale(), entity, serverdata);
	}

	@Override
	public boolean postFromServerRead() {
		return entity != null;
	}

}
