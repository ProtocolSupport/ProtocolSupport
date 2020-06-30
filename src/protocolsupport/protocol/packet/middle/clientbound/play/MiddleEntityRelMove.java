package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleEntityRelMove extends MiddleEntity {

	public MiddleEntityRelMove(ConnectionImpl connection) {
		super(connection);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity entity;
	protected short relX;
	protected short relY;
	protected short relZ;
	protected boolean onGround;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		entity = entityCache.getEntity(entityId);
		relX = serverdata.readShort();
		relY = serverdata.readShort();
		relZ = serverdata.readShort();
		onGround = serverdata.readBoolean();

		if (entity == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

	@Override
	protected void handleReadData() {
		entity.getDataCache().addLocation(relX, relY, relZ);
	}

}
