package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class MiddleEntityTeleport extends MiddleEntity {

	public MiddleEntityTeleport(MiddlePacketInit init) {
		super(init);
	}

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		entity = entityCache.getEntity(entityId);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();

		if (entity == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

	@Override
	protected void handleReadData() {
		entity.getDataCache().setLocation(x, y, z, pitch, yaw);
	}

}
