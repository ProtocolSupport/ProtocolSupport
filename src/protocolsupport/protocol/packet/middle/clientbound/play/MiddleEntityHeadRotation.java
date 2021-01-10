package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleEntityHeadRotation extends MiddleEntity {

	public MiddleEntityHeadRotation(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity entity;
	protected byte headRot;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		entity = entityCache.getEntity(entityId);
		headRot = serverdata.readByte();

		if (entity == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

	@Override
	protected void handle() {
		entity.getDataCache().setHeadYaw(headRot);
	}

}
