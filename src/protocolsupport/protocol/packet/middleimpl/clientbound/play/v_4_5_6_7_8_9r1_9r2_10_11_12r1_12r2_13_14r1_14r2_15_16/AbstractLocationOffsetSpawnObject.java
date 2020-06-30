package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractThunderboltSpawnObject;
import protocolsupport.protocol.typeremapper.entity.EntityLocationOffset;

public abstract class AbstractLocationOffsetSpawnObject extends AbstractThunderboltSpawnObject {

	protected final EntityLocationOffset entityOffset = EntityLocationOffset.get(version);

	public AbstractLocationOffsetSpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void handleReadData() {
		super.handleReadData();

		EntityLocationOffset.Offset offset = entityOffset.get(rType);
		if (offset != null) {
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			yaw += offset.getYaw();
			pitch += offset.getPitch();
		}
	}

}
