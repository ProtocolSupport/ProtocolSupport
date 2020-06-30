package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.typeremapper.entity.EntityLocationOffset;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;

public abstract class AbstractLocationOffsetEntityTeleport extends MiddleEntityTeleport {

	public AbstractLocationOffsetEntityTeleport(ConnectionImpl connection) {
		super(connection);
	}

	protected final EntityRemappingTable entityRemapTable = EntityRemappersRegistry.REGISTRY.getTable(version);
	protected final EntityLocationOffset entityOffset = EntityLocationOffset.get(version);

	@Override
	protected void handleReadData() {
		super.handleReadData();

		EntityLocationOffset.Offset offset = entityOffset.get(entityRemapTable.getRemap(entity.getType()).getLeft());
		if (offset != null) {
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			yaw += offset.getYaw();
			pitch += offset.getPitch();
		}
	}

}
