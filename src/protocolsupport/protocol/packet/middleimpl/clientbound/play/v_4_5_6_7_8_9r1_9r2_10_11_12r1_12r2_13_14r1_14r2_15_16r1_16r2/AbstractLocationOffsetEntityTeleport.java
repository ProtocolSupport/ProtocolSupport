package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry.LegacyNetworkEntityTable;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityLocationOffset;

public abstract class AbstractLocationOffsetEntityTeleport extends MiddleEntityTeleport {

	public AbstractLocationOffsetEntityTeleport(MiddlePacketInit init) {
		super(init);
	}

	protected final LegacyNetworkEntityTable legacyEntityEntryTable = LegacyNetworkEntityRegistry.INSTANCE.getTable(version);
	protected final NetworkEntityLocationOffset entityOffset = NetworkEntityLocationOffset.get(version);

	@Override
	protected void handleReadData() {
		super.handleReadData();

		NetworkEntityLocationOffset.Offset offset = entityOffset.get(legacyEntityEntryTable.get(entity.getType()).getType());
		if (offset != null) {
			x += offset.getX();
			y += offset.getY();
			z += offset.getZ();
			yaw += offset.getYaw();
			pitch += offset.getPitch();
		}
	}

}
