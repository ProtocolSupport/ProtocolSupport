package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.typeremapper.entity.EntityRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleEntityMetadata extends MiddleEntity {

	protected EntityRemapper entityRemapper = new EntityRemapper(version);

	public MiddleEntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity != null) {
			entityRemapper.readEntityWithMetadata(entity, serverdata);
		} else {
			serverdata.skipBytes(serverdata.readableBytes());
		}
	}

	@Override
	public boolean postFromServerRead() {
		if (entity != null) {
			entityRemapper.remap(true);
			return true;
		} else {
			return false;
		}
	}

}
