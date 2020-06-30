package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleEntityHeadRotation extends MiddleEntity {

	public MiddleEntityHeadRotation(ConnectionImpl connection) {
		super(connection);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity entity;
	protected byte headRot;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		entity = entityCache.getEntity(entityId);
		headRot = serverdata.readByte();

		if (entity == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

	@Override
	protected void handleReadData() {
		entity.getDataCache().setHeadYaw(headRot);
	}

}
