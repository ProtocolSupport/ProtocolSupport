package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleEntityRelMove extends MiddleEntity {

	protected MiddleEntityRelMove(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity entity;
	protected short relX;
	protected short relY;
	protected short relZ;
	protected boolean onGround;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
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
	protected void handle() {
		entity.getDataCache().addLocation(relX, relY, relZ);
	}

}
