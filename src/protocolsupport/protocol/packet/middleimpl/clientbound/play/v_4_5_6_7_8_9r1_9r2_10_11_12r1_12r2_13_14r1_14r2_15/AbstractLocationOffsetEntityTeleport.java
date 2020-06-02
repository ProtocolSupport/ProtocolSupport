package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.storage.netcache.WatchedEntityCache;
import protocolsupport.protocol.typeremapper.entity.EntityLocationOffset;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntity;

public abstract class AbstractLocationOffsetEntityTeleport extends MiddleEntityTeleport {

	protected final WatchedEntityCache entityCache = cache.getWatchedEntityCache();

	protected final EntityRemappingTable entityRemappingTable = EntityRemappersRegistry.REGISTRY.getTable(version);
	protected final EntityLocationOffset entityOffsetRemapper = EntityLocationOffset.get(version);

	public AbstractLocationOffsetEntityTeleport(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;

	@Override
	public void handleReadData() {
		entity = entityCache.getWatchedEntity(entityId);
		if (entity == null) {
			throw CancelMiddlePacketException.INSTANCE;
		}
		EntityLocationOffset.Offset offset = entityOffsetRemapper.get(entityRemappingTable.getRemap(entity.getType()).getLeft());
		if (offset != null) {
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			yaw += offset.getYaw();
			pitch += offset.getPitch();
		}
	}

}
