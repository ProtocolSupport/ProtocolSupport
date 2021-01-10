package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;

public abstract class MiddleEntityRelMoveLook extends MiddleEntity {

	public MiddleEntityRelMoveLook(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity entity;
	protected short relX;
	protected short relY;
	protected short relZ;
	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		entity = entityCache.getEntity(entityId);
		relX = serverdata.readShort();
		relY = serverdata.readShort();
		relZ = serverdata.readShort();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();

		if (entity == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

	@Override
	protected void handle() {
		NetworkEntityDataCache ecache = entity.getDataCache();
		ecache.addLocation(relX, relY, relZ);
		ecache.setLook(pitch, yaw);
	}

}
